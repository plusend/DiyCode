package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.MeEvent;
import com.plusend.diycode.event.UserEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.UserView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/28.
 */

public class UserPresenter extends Presenter {
  private static final String TAG = "UserPresenter";
  private UserView userView;
  private Data data;

  public UserPresenter(UserView userView) {
    this.userView = userView;
    this.data = NetworkData.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getMe(MeEvent event) {
    userView.getMe(event.getUser());
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getMe(UserEvent userEvent) {
    userView.getUser(userEvent.getUser());
  }

  public void getMe() {
    data.getMe();
  }

  public void getUser(String loginName) {
    data.getUser(loginName);
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
