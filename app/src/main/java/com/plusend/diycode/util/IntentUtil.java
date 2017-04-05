package com.plusend.diycode.util;

import android.content.Context;
import android.content.Intent;
import com.plusend.diycode.view.activity.UserActivity;

public class IntentUtil {
    /**
     * 调起用户详情页
     */
    public static void startUserActivity(Context context, String loginName) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(UserActivity.LOGIN_NAME, loginName);
        context.startActivity(intent);
    }
}
