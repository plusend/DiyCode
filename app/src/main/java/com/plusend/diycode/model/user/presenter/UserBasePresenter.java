package com.plusend.diycode.model.user.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.user.event.MeEvent;
import com.plusend.diycode.model.user.event.UserDetailInfoEvent;
import com.plusend.diycode.model.user.model.UserDataNetwork;
import com.plusend.diycode.model.user.view.UserView;
import com.plusend.diycode.model.base.BasePresenter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/28.
 */

public class UserBasePresenter extends BasePresenter {
  private static final String TAG = "UserPresenter";
  private UserView userView;
  private BaseData data;

  public UserBasePresenter(UserView userView) {
    this.userView = userView;
    this.data = UserDataNetwork.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getMe(MeEvent event) {
    userView.getMe(event.getUserDetailInfo());
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void getMe(UserDetailInfoEvent userDetailInfoEvent) {
    Log.d(TAG, "getMe");
    userView.getUser(userDetailInfoEvent.getUserDetailInfo());
    EventBus.getDefault().removeStickyEvent(userDetailInfoEvent);
  }

  public void getMe() {
    ((UserDataNetwork) data).getMe();
  }

  public void getUser(String loginName) {
    ((UserDataNetwork) data).getUser(loginName);
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
