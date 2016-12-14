package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.UserFavoriteTopicsEvent;
import com.plusend.diycode.event.UserTopicsEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.TopicsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/29.
 */

public class UserTopicsPresenter extends Presenter {
  private static final String TAG = "UserTopicsPresenter";

  private Data data;
  private TopicsView topicsView;

  public UserTopicsPresenter(TopicsView topicsView) {
    this.data = NetworkData.getInstance();
    this.topicsView = topicsView;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showUserCreateTopics(UserTopicsEvent userTopicsEvent) {
    Log.d(TAG, "showUserCreateTopics: " + userTopicsEvent.getTopicList().size());
    topicsView.showTopics(userTopicsEvent.getTopicList());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showUserFavoriteTopics(UserFavoriteTopicsEvent event) {
    Log.d(TAG, "showUserFavoriteTopics: " + event.getTopicList().size());
    topicsView.showTopics(event.getTopicList());
  }

  public void getUserCreateTopics(String loginName) {
    data.getUserCreateTopics(loginName, null, null);
  }

  public void getUserFavoriteTopics(String loginName) {
    data.getUserFavoriteTopics(loginName, null, null);
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
