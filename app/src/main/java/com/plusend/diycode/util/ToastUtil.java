package com.plusend.diycode.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

  public static void showText(Context context, String message) {
    if (context != null) {
      Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
  }
}
