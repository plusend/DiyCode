package com.plusend.diycode.mvp.presenter;

import com.plusend.diycode.event.NotificationsEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.NotificationsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/12/13.
 */

public class NotificationsPresenter extends Presenter {
  private Data data;
  private NotificationsView view;

  public NotificationsPresenter(NotificationsView view) {
    this.view = view;
    this.data = NetworkData.getInstance();
  }

  public void readNotifications(int offset) {
    data.readNotifications(offset, null);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showNotifications(NotificationsEvent event) {
    view.showNotifications(event.getNotificationList());
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
