package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.notification.entity.Notification;
import com.plusend.diycode.model.notification.presenter.NotificationsBasePresenter;
import com.plusend.diycode.model.notification.view.NotificationsView;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.ToastUtil;
import com.plusend.diycode.view.widget.DividerListItemDecoration;
import com.plusend.diycode.view.widget.EmptyRecyclerView;
import com.plusend.diycode.view.adapter.notification.NotificationElse;
import com.plusend.diycode.view.adapter.notification.NotificationElseViewProvider;
import com.plusend.diycode.view.adapter.notification.NotificationFollow;
import com.plusend.diycode.view.adapter.notification.NotificationFollowViewProvider;
import com.plusend.diycode.view.adapter.notification.NotificationMention;
import com.plusend.diycode.view.adapter.notification.NotificationMentionViewProvider;
import com.plusend.diycode.view.adapter.notification.NotificationReply;
import com.plusend.diycode.view.adapter.notification.NotificationReplyViewProvider;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class NotificationActivity extends BaseActivity implements NotificationsView {

  private static final String TYPE_REPLY = "TopicReply";
  private static final String TYPE_MENTION = "Mention";
  private static final String TYPE_FOLLOW = "Follow";
  private static final String TYPE_NODE_CHANGED = "NodeChanged";
  private static final String MENTION_TYPE_NEWS = "HacknewsReply";
  private static final String MENTION_TYPE_REPLY = "Reply";
  @BindView(R.id.activity_notification) EmptyRecyclerView recyclerView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.empty_view) TextView emptyView;

  private MultiTypeAdapter adapter;
  private LinearLayoutManager linearLayoutManager;
  private Items items;
  private NotificationsBasePresenter presenter;
  private int offset;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_notification);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    presenter = new NotificationsBasePresenter(this);
    items = new Items();
    adapter = new MultiTypeAdapter(items);
    adapter.register(NotificationReply.class, new NotificationReplyViewProvider());
    adapter.register(NotificationMention.class, new NotificationMentionViewProvider());
    adapter.register(NotificationFollow.class, new NotificationFollowViewProvider());
    adapter.register(NotificationElse.class, new NotificationElseViewProvider());
    linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(adapter);
    recyclerView.setEmptyView(emptyView);
    recyclerView.addItemDecoration(new DividerListItemDecoration(getContext()));
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

    if (TextUtils.isEmpty(Constant.VALUE_TOKEN)) {
      startActivityForResult(new Intent(this, SignInActivity.class), SignInActivity.REQUEST_CODE);
      ToastUtil.showText(this, "请先登录");
    } else {
      getNotifications();
    }
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<BasePresenter> getPresenter() {
    return super.addPresenter(presenter);
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
        if (MENTION_TYPE_REPLY.equals(notification.getMentionType())) {
          NotificationMention mention =
              new NotificationMention(notification.getActor().getAvatarUrl(),
                  notification.getActor().getLogin(),
                  String.valueOf(notification.getMention().getId()),
                  notification.getMention().getBodyHtml(), notification.getMention().getTopicId());
          items.add(mention);
        } else if (MENTION_TYPE_NEWS.equals(notification.getMentionType())) {
          // 这里的 api 有个 bug：这种情况下没有返回 mention 的值
          NotificationMention mention =
              new NotificationMention(notification.getActor().getAvatarUrl(),
                  notification.getActor().getLogin(), "HacknewsReply", "提到了你", 411);
          items.add(mention);
        }
      } else if (TYPE_FOLLOW.equals(notification.getType())) {
        NotificationFollow follow = new NotificationFollow(notification.getActor().getAvatarUrl(),
            notification.getActor().getLogin());
        items.add(follow);
      } else {
        NotificationElse notificationElse = new NotificationElse(notification.getType());
        items.add(notificationElse);
      }
    }
    offset = items.size();
    adapter.notifyDataSetChanged();
  }

  private void getNotifications() {
    presenter.readNotifications(offset);
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode == SignInActivity.RESULT_OK) {
          getNotifications();
        } else {
          ToastUtil.showText(this, "放弃登录");
          finish();
        }
        break;
    }
  }
}
