package com.plusend.diycode.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.entity.Notification;
import com.plusend.diycode.mvp.presenter.NotificationsPresenter;
import com.plusend.diycode.mvp.view.NotificationsView;
import com.plusend.diycode.view.adapter.MyDecoration;
import com.plusend.diycode.view.adapter.notification.NotificationMention;
import com.plusend.diycode.view.adapter.notification.NotificationMentionViewProvider;
import com.plusend.diycode.view.adapter.notification.NotificationReply;
import com.plusend.diycode.view.adapter.notification.NotificationReplyViewProvider;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class NotificationActivity extends AppCompatActivity implements NotificationsView {

  private static final String TYPE_REPLY = "TopicReply";
  private static final String TYPE_MENTION = "Mention";
  @BindView(R.id.activity_notification) RecyclerView recyclerView;
  @BindView(R.id.toolbar) Toolbar toolbar;

  private MultiTypeAdapter adapter;
  private LinearLayoutManager linearLayoutManager;
  private Items items;
  private NotificationsPresenter presenter;
  private int offset;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification);
    ButterKnife.bind(this);
    initActionBar(toolbar);

    presenter = new NotificationsPresenter(this);
    items = new Items();
    adapter = new MultiTypeAdapter(items);
    adapter.register(NotificationReply.class, new NotificationReplyViewProvider());
    adapter.register(NotificationMention.class, new NotificationMentionViewProvider());
    linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(adapter);
    recyclerView.addItemDecoration(new MyDecoration(getContext()));
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          //adapter.setStatus(TopicsAdapter.STATUS_LOADING);
          //adapter.notifyDataSetChanged();
          presenter.readNotifications(offset);
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

  @Override public void showNotifications(List<Notification> notificationList) {
    if (notificationList == null || notificationList.isEmpty()) {
      return;
    }

    for (Notification notification : notificationList) {
      if (TYPE_REPLY.equals(notification.getType())) {
        NotificationReply reply = new NotificationReply(notification.getActor().getAvatarUrl(),
            notification.getActor().getLogin(), notification.getReply().getTopicTitle(),
            notification.getReply().getBodyHtml(), notification.getReply().getTopicId());
        items.add(reply);
      } else if (TYPE_MENTION.equals(notification.getType())) {
        NotificationMention mention =
            new NotificationMention(notification.getActor().getAvatarUrl(),
                notification.getActor().getLogin(),
                String.valueOf(notification.getMention().getId()),
                notification.getMention().getBodyHtml(), notification.getMention().getTopicId());
        items.add(mention);
      }
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
    presenter.readNotifications(offset);
  }

  @Override protected void onStop() {
    presenter.stop();
    super.onStop();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
