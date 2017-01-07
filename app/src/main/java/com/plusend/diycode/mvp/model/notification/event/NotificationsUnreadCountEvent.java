package com.plusend.diycode.mvp.model.notification.event;

import com.plusend.diycode.mvp.model.notification.entity.NotificationsUnreadCount;

public class NotificationsUnreadCountEvent {
  private NotificationsUnreadCount notificationsUnreadCount;

  public NotificationsUnreadCountEvent(NotificationsUnreadCount notificationsUnreadCount) {
    this.notificationsUnreadCount = notificationsUnreadCount;
  }

  public NotificationsUnreadCount getNotificationsUnreadCount() {
    return notificationsUnreadCount;
  }
}
