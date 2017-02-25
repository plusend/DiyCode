package com.plusend.diycode.model.topic.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.model.topic.event.TopicsEvent;
import com.plusend.diycode.model.topic.view.TopicsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicsPresenter extends BasePresenter {
  private static final String TAG = "TopicsPresenter";

  private BaseData data;
  private TopicsView topicsView;

  public TopicsPresenter(TopicsView topicsView) {
    this.data = TopicDataNetwork.getInstance();
    this.topicsView = topicsView;
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showTopics(TopicsEvent topicsEvent) {
    Log.d(TAG, "showTopics: " + topicsEvent.getTopicList());
    topicsView.showTopics(topicsEvent.getTopicList());
  }

  public void getTopics(Integer offset) {
    Log.d(TAG, "getTopics: offset: " + offset);
    ((TopicDataNetwork) data).getTopics(null, null, offset, null);
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
