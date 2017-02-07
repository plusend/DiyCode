package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;
import com.plusend.diycode.mvp.model.topic.entity.TopicReply;
import com.plusend.diycode.mvp.model.topic.presenter.TopicPresenter;
import com.plusend.diycode.mvp.model.topic.presenter.TopicRepliesPresenter;
import com.plusend.diycode.mvp.model.topic.view.TopicRepliesView;
import com.plusend.diycode.mvp.model.topic.view.TopicView;
import com.plusend.diycode.view.adapter.DividerListItemDecoration;
import com.plusend.diycode.view.adapter.topic.FooterViewProvider;
import com.plusend.diycode.view.adapter.topic.TopicDetailViewProvider;
import com.plusend.diycode.view.adapter.topic.Footer;
import com.plusend.diycode.view.adapter.topic.TopicReplyViewProvider;
import com.plusend.diycode.view.adapter.topic.TopicReplyWithTopic;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TopicActivity extends AppCompatActivity implements TopicView, TopicRepliesView {
  private static final String TAG = "TopicActivity";
  public static final String ID = "topicId";

  @BindView(R.id.rv) RecyclerView rv;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.toolbar) Toolbar toolbar;

  private int id;
  private TopicDetail topicDetail;
  private MultiTypeAdapter adapter;
  private Items items = new Items();
  private TopicPresenter topicPresenter;
  private TopicRepliesPresenter topicRepliesPresenter;
  private LinearLayoutManager linearLayoutManager;
  private int offset;
  private boolean noMoreReplies;

  @Override protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_topic);
    ButterKnife.bind(this);
    initActionBar(toolbar);

    Intent intent = getIntent();
    id = intent.getIntExtra(ID, 0);
    Log.d(TAG, "id: " + id);
    linearLayoutManager = new LinearLayoutManager(this);
    rv.setLayoutManager(linearLayoutManager);

    adapter = new MultiTypeAdapter(items);
    adapter.register(TopicDetail.class, new TopicDetailViewProvider());
    adapter.register(TopicReplyWithTopic.class, new TopicReplyViewProvider());
    adapter.register(Footer.class, new FooterViewProvider());

    rv.setAdapter(adapter);
    rv.addItemDecoration(new DividerListItemDecoration(this));
    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!noMoreReplies
            && newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          Log.d(TAG, "add more: offset " + offset);
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_LOADING);
          adapter.notifyItemChanged(adapter.getItemCount());
          topicRepliesPresenter.addReplies(offset);
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });

    topicPresenter = new TopicPresenter(this);
    topicRepliesPresenter = new TopicRepliesPresenter(this, id);
    if (id != 0) {
      topicPresenter.getTopic(id);
    }

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(TopicActivity.this, CreateTopicReplyActivity.class);
        intent.putExtra(CreateTopicReplyActivity.TOPIC_ID, topicDetail.getId());
        intent.putExtra(CreateTopicReplyActivity.TOPIC_TITLE, topicDetail.getTitle());
        startActivity(intent);
      }
    });
  }

  private void initActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override public void showTopic(TopicDetail topicDetail) {
    items.add(topicDetail);
    this.topicDetail = topicDetail;
    //topicRepliesAdapter.setTopicDetail(topicDetail);
    //topicRepliesAdapter.notifyItemInserted(0);
    adapter.notifyItemInserted(adapter.getItemCount());
    requestReplies();
  }

  private void requestReplies() {
    topicRepliesPresenter.getReplies();
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onStart() {
    super.onStart();
    topicPresenter.start();
    topicRepliesPresenter.start();
  }

  @Override protected void onStop() {
    topicPresenter.stop();
    topicRepliesPresenter.stop();
    super.onStop();
  }

  @Override public void showReplies(List<TopicReply> topicReplyList) {
    if (topicReplyList != null) {
      for (TopicReply topicReply : topicReplyList) {
        items.add(new TopicReplyWithTopic(this.topicDetail, topicReply));
        adapter.notifyItemInserted(adapter.getItemCount());
      }
      offset = this.items.size() - 1;// 去除 Header
      switch (topicReplyList.size()) {
        case 20:
          noMoreReplies = false;
          items.add(new Footer(Footer.STATUS_NORMAL));
          adapter.notifyItemInserted(adapter.getItemCount());
          break;
        case 0:
          noMoreReplies = true;
          items.add(new Footer(Footer.STATUS_NO_MORE));
          adapter.notifyItemInserted(adapter.getItemCount());
          break;
        default:
          noMoreReplies = true;
          items.add(new Footer(Footer.STATUS_NO_MORE));
          adapter.notifyItemInserted(adapter.getItemCount());
          break;
      }
    }
  }

  @Override public void addReplies(List<TopicReply> topicReplyList) {
    if (topicReplyList != null) {
      for (TopicReply topicReply : topicReplyList) {
        // 插入 FooterView 前面
        items.add(items.size() - 1, new TopicReplyWithTopic(this.topicDetail, topicReply));
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
      }
      switch (topicReplyList.size()) {
        case 20:
          noMoreReplies = false;
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NORMAL);
          adapter.notifyItemChanged(adapter.getItemCount());
          break;
        case 0:
          noMoreReplies = true;
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
          adapter.notifyItemChanged(adapter.getItemCount());
          break;
        default:
          noMoreReplies = true;
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
          adapter.notifyItemChanged(adapter.getItemCount());
          break;
      }
      offset = this.items.size() - 2; // 去除 Footer & Header
    }
  }

  @Override public void showNewReply() {
    Log.d(TAG, "add more: offset " + offset);
    ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_LOADING);
    adapter.notifyItemChanged(adapter.getItemCount());
    topicRepliesPresenter.addReplies(offset);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
