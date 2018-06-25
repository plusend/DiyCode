package com.plusend.diycode.model.notification.data;

import android.util.Log;
import com.plusend.diycode.model.notification.entity.Notification;
import com.plusend.diycode.model.notification.entity.NotificationDelete;
import com.plusend.diycode.model.notification.entity.NotificationsUnreadCount;
import com.plusend.diycode.model.notification.event.NotificationDeleteEvent;
import com.plusend.diycode.model.notification.event.NotificationsEvent;
import com.plusend.diycode.model.notification.event.NotificationsUnreadCountEvent;
import com.plusend.diycode.util.Constant;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationDataNetwork implements NotificationData {
    private static final String TAG = "NetworkData";
    private static NotificationDataNetwork networkData = new NotificationDataNetwork();
    private NotificationService service;

    private NotificationDataNetwork() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        service = retrofit.create(NotificationService.class);
    }

    public static NotificationDataNetwork getInstance() {
        return networkData;
    }

    @Override public void readNotifications(Integer offset, Integer limit) {
        Call<List<Notification>> call =
            service.readNotifications(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, offset,
                limit);
        call.enqueue(new Callback<List<Notification>>() {
            @Override public void onResponse(Call<List<Notification>> call,
                Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    List<Notification> notificationList = response.body();
                    Log.v(TAG, "notificationList: " + notificationList);
                    EventBus.getDefault().postSticky(new NotificationsEvent(notificationList));
                } else {
                    Log.e(TAG, "readNotifications STATUS: " + response.code());
                    EventBus.getDefault().postSticky(new NotificationsEvent(null));
                }
            }

            @Override public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().postSticky(new NotificationsEvent(null));
            }
        });
    }

    @Override public void deleteNotification(int id) {
        Call<NotificationDelete> call =
            service.deleteNotification(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, id);
        call.enqueue(new Callback<NotificationDelete>() {
            @Override public void onResponse(Call<NotificationDelete> call,
                Response<NotificationDelete> response) {
                if (response.isSuccessful()) {
                    NotificationDelete notificationDelete = response.body();
                    Log.v(TAG, "notificationDelete: " + notificationDelete);
                    EventBus.getDefault().post(new NotificationDeleteEvent(notificationDelete));
                } else {
                    Log.e(TAG, "deleteNotification STATUS: " + response.code());
                    EventBus.getDefault().post(new NotificationDeleteEvent(null));
                }
            }

            @Override public void onFailure(Call<NotificationDelete> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new NotificationDeleteEvent(null));
            }
        });
    }

    @Override public void deleteAllNotifications() {

    }

    @Override public void markNotificationsAsRead(int[] ids) {

    }

    @Override public void unReadNotificationCount() {
        Call<NotificationsUnreadCount> call =
            service.unReadNotificationCount(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN);
        call.enqueue(new Callback<NotificationsUnreadCount>() {
            @Override public void onResponse(Call<NotificationsUnreadCount> call,
                Response<NotificationsUnreadCount> response) {
                if (response.isSuccessful()) {
                    NotificationsUnreadCount notificationsUnreadCount = response.body();
                    Log.v(TAG, "notificationsUnreadCount: " + notificationsUnreadCount);
                    EventBus.getDefault()
                        .post(new NotificationsUnreadCountEvent(notificationsUnreadCount));
                } else {
                    Log.e(TAG, "unReadNotificationCount STATUS: " + response.code());
                    EventBus.getDefault().post(new NotificationsUnreadCountEvent(null));
                }
            }

            @Override public void onFailure(Call<NotificationsUnreadCount> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new NotificationsUnreadCountEvent(null));
            }
        });
    }
}
