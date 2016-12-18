package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.TokenEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.SignInView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/28.
 */

public class SignInPresenter extends Presenter {
  private static final String TAG = "SignInPresenter";
  private SignInView signInView;
  private Data data;

  public SignInPresenter(SignInView signInView) {
    this.signInView = signInView;
    this.data = NetworkData.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getToken(TokenEvent tokenEvent) {
    signInView.getToken(tokenEvent.getToken());
  }

  public void getToken(String username, String password) {
    data.getToken(username, password);
  }

  @Override public void start() {
    Log.d(TAG, "register");
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    Log.d(TAG, "unregister");
    EventBus.getDefault().unregister(this);
  }
}
