package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.CreateTopicReplyEvent;
import com.plusend.diycode.event.NewTopicEvent;
import com.plusend.diycode.event.TopicDetailEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.TopicView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/25.
 */

public class TopicPresenter extends Presenter {
  private static final String TAG = "TopicPresenter";
  private Data data;
  private TopicView topicView;
  private int id;

  public TopicPresenter(TopicView topicView) {
    this.data = NetworkData.getInstance();
    this.topicView = topicView;
  }

  public void getTopic(int id){
    this.id = id;
    data.getTopic(id);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showTopic(TopicDetailEvent topicDetailEvent) {
    Log.d(TAG, "showTopic");
    topicView.showTopic(topicDetailEvent.getTopicDetail());
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) public void showNewTopic(NewTopicEvent newTopicEvent) {
    Log.d(TAG, "showNewTopic");
    topicView.showTopic(newTopicEvent.getTopicDetail());
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) public void getNewTopicReply(CreateTopicReplyEvent createTopicReplyEvent) {
    Log.d(TAG, "getNewTopicReply");
    getTopic(id);
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
