package com.plusend.diycode.mvp.model.topic.presenter;

import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.mvp.model.topic.event.NewTopicEvent;
import com.plusend.diycode.mvp.model.topic.view.NewTopicView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewTopicPresenter extends Presenter {
  private BaseData data;
  private NewTopicView newTopicView;

  public NewTopicPresenter(NewTopicView newTopicView) {
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
