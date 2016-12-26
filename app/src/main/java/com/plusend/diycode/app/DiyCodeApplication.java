package com.plusend.diycode.app;

import android.app.Application;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.KeyStoreHelper;
import im.fir.sdk.FIR;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by plusend on 2016/12/18.
 */

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
  }
}
