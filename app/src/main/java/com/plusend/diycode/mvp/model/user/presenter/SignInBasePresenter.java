package com.plusend.diycode.mvp.model.user.presenter;

import android.util.Log;
import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.BasePresenter;
import com.plusend.diycode.mvp.model.user.event.TokenEvent;
import com.plusend.diycode.mvp.model.user.model.UserDataNetwork;
import com.plusend.diycode.mvp.model.user.view.SignInView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SignInBasePresenter extends BasePresenter {
  private static final String TAG = "SignInPresenter";
  private SignInView signInView;
  private BaseData data;

  public SignInBasePresenter(SignInView signInView) {
    this.signInView = signInView;
    this.data = UserDataNetwork.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getToken(TokenEvent tokenEvent) {
    signInView.getToken(tokenEvent.getToken());
  }

  public void getToken(String username, String password) {
    ((UserDataNetwork) data).getToken(username, password);
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
