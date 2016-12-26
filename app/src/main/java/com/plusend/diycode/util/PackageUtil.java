package com.plusend.diycode.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageUtil {
  public static int getVersionCode(Context context) {
    int version;
    PackageManager packageManager = context.getPackageManager();
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    PackageInfo packInfo = null;
    try {
      packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    version = packInfo != null ? packInfo.versionCode : 0;
    return version;
  }
}
