package com.plusend.diycode.mvp.model.notification.view;

import com.plusend.diycode.mvp.model.notification.entity.Notification;
import com.plusend.diycode.mvp.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/12/13.
 */

public interface NotificationsView extends BaseView {
  void showNotifications(List<Notification> notificationList);
}
