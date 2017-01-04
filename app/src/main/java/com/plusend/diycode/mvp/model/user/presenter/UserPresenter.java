package com.plusend.diycode.mvp.model.user.presenter;

import android.util.Log;
import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.user.event.MeEvent;
import com.plusend.diycode.mvp.model.user.event.UserDetailInfoEvent;
import com.plusend.diycode.mvp.model.topic.data.TopicData;
import com.plusend.diycode.mvp.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.mvp.model.user.model.UserDataNetwork;
import com.plusend.diycode.mvp.model.user.view.UserView;
import com.plusend.diycode.mvp.model.base.Presenter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/28.
 */

public class UserPresenter extends Presenter {
  private static final String TAG = "UserPresenter";
  private UserView userView;
  private BaseData data;

  public UserPresenter(UserView userView) {
    this.userView = userView;
    this.data = UserDataNetwork.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getMe(MeEvent event) {
    userView.getMe(event.getUserDetailInfo());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void getMe(UserDetailInfoEvent userDetailInfoEvent) {
    userView.getUser(userDetailInfoEvent.getUserDetailInfo());
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
