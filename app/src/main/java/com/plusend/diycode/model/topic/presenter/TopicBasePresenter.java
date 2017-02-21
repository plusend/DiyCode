package com.plusend.diycode.model.topic.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.model.topic.event.LoadTopicDetailFinishEvent;
import com.plusend.diycode.model.topic.event.NewTopicEvent;
import com.plusend.diycode.model.topic.event.TopicDetailEvent;
import com.plusend.diycode.model.topic.view.TopicView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicBasePresenter extends BasePresenter {
  private static final String TAG = "TopicPresenter";
  private BaseData data;
  private TopicView topicView;
  private int id;

  public TopicBasePresenter(TopicView topicView) {
    this.data = TopicDataNetwork.getInstance();
    this.topicView = topicView;
  }

  public void getTopic(int id) {
    this.id = id;
    ((TopicDataNetwork) data).getTopic(id);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showTopic(TopicDetailEvent topicDetailEvent) {
    Log.d(TAG, "showTopic");
    topicView.showTopic(topicDetailEvent.getTopicDetail());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void loadTopicFinish(LoadTopicDetailFinishEvent event) {
    Log.d(TAG, "loadTopicFinish");
    topicView.loadTopicFinish();
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void showNewTopic(NewTopicEvent newTopicEvent) {
    Log.d(TAG, "showNewTopic");
    topicView.showTopic(newTopicEvent.getTopicDetail());
    EventBus.getDefault().removeStickyEvent(newTopicEvent);
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
