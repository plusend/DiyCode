package com.plusend.diycode.mvp.model.notification.data;

import com.plusend.diycode.mvp.model.base.BaseData;

public interface NotificationData extends BaseData {

  /**
   * 获取当前用户的通知列表
   */
  void readNotifications(Integer offset, Integer limit);

  /**
   * 删除当前用户的某个通知
   */
  void deleteNotification(int id);

  /**
   * 删除当前用户的所有通知
   */
  void deleteAllNotifications();

  /**
   * 将当前用户的一些通知设成已读状态
   */
  void markNotificationsAsRead(int[] ids);

  /**
   * 获得未读通知数量
   */
  void unReadNotificationCount();
}
