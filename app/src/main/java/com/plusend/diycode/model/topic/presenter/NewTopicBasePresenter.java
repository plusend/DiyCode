package com.plusend.diycode.model.topic.presenter;

import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.model.topic.event.NewTopicEvent;
import com.plusend.diycode.model.topic.view.NewTopicView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewTopicBasePresenter extends BasePresenter {
  private BaseData data;
  private NewTopicView newTopicView;

  public NewTopicBasePresenter(NewTopicView newTopicView) {
    this.newTopicView = newTopicView;
    this.data = TopicDataNetwork.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getNewTopic(NewTopicEvent newTopicEvent) {
    newTopicView.getNewTopic(newTopicEvent.getTopicDetail());
  }

  public void newTopic(String title, String body, int node_id) {
    ((TopicDataNetwork) data).newTopic(title, body, node_id);
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
