package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.Notification;
import java.util.List;

/**
 * Created by plusend on 2016/12/13.
 */

public interface NotificationsView extends BaseView {
  void showNotifications(List<Notification> notificationList);
}
