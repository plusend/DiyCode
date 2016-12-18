package com.plusend.diycode.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by plusend on 2016/12/18.
 */

public class ToastUtil {
  
  public static void showText(Context context, String message) {
    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }
}
