package com.plusend.diycode.mvp.presenter;

import com.plusend.diycode.event.NewTopicEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.NewTopicView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/30.
 */

public class NewTopicPresenter extends Presenter {
  private Data data;
  private NewTopicView newTopicView;

  public NewTopicPresenter(NewTopicView newTopicView) {
    this.newTopicView = newTopicView;
    this.data = NetworkData.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getNewTopic(NewTopicEvent newTopicEvent) {
    newTopicView.getNewTopic(newTopicEvent.getTopicDetail());
  }

  public void newTopic(String title, String body, int node_id) {
    data.newTopic(title, body, node_id);
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
