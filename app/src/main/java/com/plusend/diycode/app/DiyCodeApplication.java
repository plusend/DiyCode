package com.plusend.diycode.app;

import android.app.Application;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.KeyStoreHelper;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by plusend on 2016/12/18.
 */

public class DiyCodeApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    try {
      KeyStoreHelper.createKeys(getApplicationContext(), Constant.KEYSTORE_KEY_ALIAS);
    } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }
  }
}
