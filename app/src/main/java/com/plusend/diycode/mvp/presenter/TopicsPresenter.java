package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.TopicsEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.TopicsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/24.
 */

public class TopicsPresenter extends Presenter {
  private static final String TAG = "TopicsPresenter";

  private Data data;
  private TopicsView topicsView;

  public TopicsPresenter(TopicsView topicsView) {
    this.data = NetworkData.getInstance();
    this.topicsView = topicsView;
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showTopics(TopicsEvent topicsEvent) {
    Log.d(TAG, "showTopics: " + topicsEvent.getTopicList());
    topicsView.showTopics(topicsEvent.getTopicList());
  }

  public void getTopics(Integer offset) {
    Log.d(TAG, "getTopics: offset: " + offset);
    data.getTopics(null, null, offset, null);
  }

  public void getUserCreateTopics(String loginName, Integer offset) {
    Log.d(TAG, "getUserCreateTopics: loginName: " + loginName + " offset: " + offset);
    data.getUserCreateTopics(loginName, offset, null);
  }

  public void getUserFavoriteTopics(String loginName, Integer offset) {
    data.getUserFavoriteTopics(loginName, offset, null);
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
