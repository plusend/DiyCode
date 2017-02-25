package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.entity.TopicDetail;
import com.plusend.diycode.model.topic.entity.TopicReply;
import com.plusend.diycode.model.topic.presenter.TopicPresenter;
import com.plusend.diycode.model.topic.presenter.TopicRepliesPresenter;
import com.plusend.diycode.model.topic.view.TopicRepliesView;
import com.plusend.diycode.model.topic.view.TopicView;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.view.adapter.topic.Footer;
import com.plusend.diycode.view.adapter.topic.FooterViewProvider;
import com.plusend.diycode.view.adapter.topic.TopicDetailViewProvider;
import com.plusend.diycode.view.adapter.topic.TopicReplyViewProvider;
import com.plusend.diycode.view.adapter.topic.TopicReplyWithTopic;
import com.plusend.diycode.view.widget.DividerListItemDecoration;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.plusend.diycode.R.id.topic;

public class TopicActivity extends BaseActivity implements TopicView, TopicRepliesView {
  private static final String TAG = "TopicActivity";
  public static final String ID = "topicId";

  @BindView(R.id.rv) RecyclerView rv;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.coordinator) CoordinatorLayout coordinator;

  private TopicDetail topicDetail;
  private MultiTypeAdapter adapter;
  private Items items = new Items();
  private TopicPresenter topicPresenter;
  private TopicRepliesPresenter topicRepliesPresenter;
  private LinearLayoutManager linearLayoutManager;
  private int topicId;
  private int offset;
  private boolean noMoreReplies;

  @Override protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    setContentView(R.layout.activity_topic);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    topicId = intent.getIntExtra(ID, 0);
    Log.d(TAG, "topicId: " + topicId);
    linearLayoutManager = new LinearLayoutManager(this);
    rv.setLayoutManager(linearLayoutManager);

    topicPresenter = new TopicPresenter(this);

    View.OnClickListener favoriteListener = new View.OnClickListener() {
      @Override public void onClick(View v) {
        String loginName = PrefUtil.getMe(TopicActivity.this).getLogin();
        if (TextUtils.isEmpty(loginName)) {
          Snackbar.make(coordinator, "请先登录", Snackbar.LENGTH_LONG)
              .setAction("登录", new View.OnClickListener() {
                @Override public void onClick(View v) {
                  startActivityForResult(new Intent(TopicActivity.this, SignInActivity.class),
                      SignInActivity.REQUEST_CODE);
                }
              })
              .show();
          return;
        }
        topicPresenter.favoriteTopic(topicId);
      }
    };

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

    topicRepliesPresenter = new TopicRepliesPresenter(this, topicId);
    if (topicId != 0) {
      topicPresenter.getTopic(topicId);
    }

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String loginName = PrefUtil.getMe(TopicActivity.this).getLogin();
        if (TextUtils.isEmpty(loginName)) {
          Snackbar.make(coordinator, "请先登录", Snackbar.LENGTH_LONG)
              .setAction("登录", new View.OnClickListener() {
                @Override public void onClick(View v) {
                  startActivityForResult(new Intent(TopicActivity.this, SignInActivity.class),
                      SignInActivity.REQUEST_CODE);
                }
              })
              .show();
          return;
        }
        Intent intent = new Intent(TopicActivity.this, CreateTopicReplyActivity.class);
        intent.putExtra(CreateTopicReplyActivity.TOPIC_ID, topicDetail.getId());
        intent.putExtra(CreateTopicReplyActivity.TOPIC_TITLE, topicDetail.getTitle());
        startActivity(intent);
      }
    });
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<BasePresenter> getPresenter() {
    return super.addPresenter(topicPresenter, topicRepliesPresenter);
  }

  @Override public void showTopic(TopicDetail topicDetail) {
    items.add(topicDetail);
    this.topicDetail = topicDetail;
    adapter.notifyItemInserted(adapter.getItemCount());
    requestReplies();
  }

  @Override public void loadTopicFinish() {
    progressBar.setVisibility(View.GONE);
    rv.setVisibility(View.VISIBLE);
    fab.setVisibility(View.VISIBLE);
  }

  @Override public void showFavorite(boolean bool) {
    if (bool) {
      ((TopicDetail) items.get(0)).setFavorited(true);
      adapter.notifyItemChanged(0);
    } else {
      Snackbar.make(coordinator, "收藏失败", Snackbar.LENGTH_LONG).show();
    }
  }

  @Override public void showUnFavorite(boolean bool) {
    if (bool) {
      ((TopicDetail) items.get(0)).setFavorited(false);
      adapter.notifyItemChanged(0);
    }
  }

  @Override public void showFollow(boolean bool) {

  }

  @Override public void showUnFollow(boolean bool) {

  }

  @Override public void showLike(boolean bool) {

  }

  @Override public void showUnLike(boolean bool) {

  }

  private void requestReplies() {
    topicRepliesPresenter.getReplies();
  }

  @Override public Context getContext() {
    return this;
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

  @Override protected void onPause() {
    if (topicDetail.isFavorited()) {
      topicPresenter.favoriteTopic(topicId);
    } else {
      topicPresenter.unFavoriteTopic(topicId);
    }
    super.onPause();
  }
}
