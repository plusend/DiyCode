package com.plusend.diycode.model.notification.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.notification.data.NotificationDataNetwork;
import com.plusend.diycode.model.notification.event.NotificationsEvent;
import com.plusend.diycode.model.notification.view.NotificationsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NotificationsBasePresenter extends BasePresenter {
    private static final String TAG = "NotificationsPresenter";
    private BaseData topicData;
    private NotificationsView view;

    public NotificationsBasePresenter(NotificationsView view) {
        this.view = view;
        this.topicData = NotificationDataNetwork.getInstance();
    }

    public void readNotifications(int offset) {
        Log.d(TAG, "readNotifications");
        ((NotificationDataNetwork) topicData).readNotifications(offset, null);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void showNotifications(NotificationsEvent event) {
        Log.d(TAG, "showNotifications");
        view.showNotifications(event.getNotificationList());
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override public void start() {
        EventBus.getDefault().register(this);
    }

    @Override public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
