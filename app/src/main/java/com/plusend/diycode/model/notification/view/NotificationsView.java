package com.plusend.diycode.model.notification.view;

import com.plusend.diycode.model.base.BaseView;
import com.plusend.diycode.model.notification.entity.Notification;
import java.util.List;

/**
 * Created by plusend on 2016/12/13.
 */

public interface NotificationsView extends BaseView {
    void showNotifications(List<Notification> notificationList);
}
