package com.plusend.diycode.app;

import android.app.Application;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.KeyStoreHelper;
import im.fir.sdk.FIR;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class DiyCodeApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    
    // 初始化 BugHD
    FIR.init(this);

    try {
      KeyStoreHelper.createKeys(getApplicationContext(), Constant.KEYSTORE_KEY_ALIAS);
    } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }

    // 滑动返回
    BGASwipeBackManager.getInstance().init(this);

    Fresco.initialize(this);
  }
}
