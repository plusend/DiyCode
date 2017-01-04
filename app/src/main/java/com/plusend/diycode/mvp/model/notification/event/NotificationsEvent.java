package com.plusend.diycode.mvp.model.notification.event;

import com.plusend.diycode.mvp.model.notification.entity.Notification;
import java.util.List;

/**
 * Created by plusend on 2016/12/12.
 */

public class NotificationsEvent {
  private List<Notification> notificationList;

  public NotificationsEvent(List<Notification> notificationList) {
    this.notificationList = notificationList;
  }

  public List<Notification> getNotificationList() {
    return notificationList;
  }
}
