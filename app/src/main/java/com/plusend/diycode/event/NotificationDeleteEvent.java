package com.plusend.diycode.event;

import com.plusend.diycode.mvp.model.entity.NotificationDelete;

/**
 * Created by plusend on 2016/12/13.
 */

public class NotificationDeleteEvent {
  private NotificationDelete notificationDelete;

  public NotificationDeleteEvent(NotificationDelete notificationDelete) {
    this.notificationDelete = notificationDelete;
  }

  public NotificationDelete getNotificationDelete() {
    return notificationDelete;
  }
}
