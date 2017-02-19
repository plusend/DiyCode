package com.plusend.diycode.app;

import android.app.Application;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import com.pgyersdk.crash.PgyCrashManager;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.KeyStoreHelper;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class DiyCodeApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    // 初始化蒲公英
    PgyCrashManager.register(this);

    try {
      KeyStoreHelper.createKeys(getApplicationContext(), Constant.KEYSTORE_KEY_ALIAS);
    } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }

    // 滑动返回
    BGASwipeBackManager.getInstance().init(this);
  }
}
