package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.entity.Reply;
import com.plusend.diycode.mvp.presenter.RepliesPresenter;
import com.plusend.diycode.mvp.view.RepliesView;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.view.adapter.DividerListItemDecoration;
import com.plusend.diycode.view.adapter.EmptyRecyclerView;
import com.plusend.diycode.view.adapter.notification.NotificationMention;
import com.plusend.diycode.view.adapter.notification.NotificationMentionViewProvider;
import com.plusend.diycode.view.adapter.notification.NotificationReply;
import com.plusend.diycode.view.adapter.notification.NotificationReplyViewProvider;
import com.plusend.diycode.view.adapter.reply.ReplyViewProvider;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MyRepliesActivity extends AppCompatActivity implements RepliesView {
  private static final String TAG = "MyRepliesActivity";
  public static final String LOGIN_NAME = "loginName";

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.rv_replies) EmptyRecyclerView rvReplies;
  @BindView(R.id.empty_view) TextView emptyView;

  private MultiTypeAdapter adapter;
  private LinearLayoutManager linearLayoutManager;
  private Items items;
  private RepliesPresenter presenter;
  private int offset;
  private String loginName;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_replies);
    ButterKnife.bind(this);
    initActionBar(toolbar);

    loginName = PrefUtil.getMe(this).getLogin();

    presenter = new RepliesPresenter(this);
    items = new Items();
    adapter = new MultiTypeAdapter(items);
    adapter.register(Reply.class, new ReplyViewProvider());
    linearLayoutManager = new LinearLayoutManager(this);
    rvReplies.setLayoutManager(linearLayoutManager);
    rvReplies.setAdapter(adapter);
    rvReplies.setEmptyView(emptyView);
    rvReplies.addItemDecoration(new DividerListItemDecoration(getContext()));
    rvReplies.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          //adapter.setStatus(TopicsAdapter.STATUS_LOADING);
          //adapter.notifyDataSetChanged();
          presenter.getReplies(loginName, offset);
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void showReplies(List<Reply> replyList) {
    if (replyList == null || replyList.isEmpty()) {
      return;
    }
    for (Reply reply : replyList) {
      items.add(reply);
    }
    offset = items.size();
    adapter.notifyDataSetChanged();
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onStart() {
    super.onStart();
    presenter.start();
    presenter.getReplies(loginName, offset);
  }

  @Override protected void onStop() {
    presenter.stop();
    super.onStop();
  }
}
