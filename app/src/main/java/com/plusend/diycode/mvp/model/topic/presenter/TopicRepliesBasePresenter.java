package com.plusend.diycode.mvp.model.topic.presenter;

import android.util.Log;
import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.BasePresenter;
import com.plusend.diycode.mvp.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.mvp.model.topic.event.CreateTopicReplyEvent;
import com.plusend.diycode.mvp.model.topic.event.TopicRepliesEvent;
import com.plusend.diycode.mvp.model.topic.view.TopicRepliesView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicRepliesBasePresenter extends BasePresenter {
  private static final String TAG = "TopicRepliesPresenter";
  private TopicRepliesView topicRepliesView;
  private BaseData data;
  private int id;
  private int status;

  public TopicRepliesBasePresenter(TopicRepliesView topicRepliesView, int id) {
    this.topicRepliesView = topicRepliesView;
    this.data = TopicDataNetwork.getInstance();
    this.id = id;
  }

  public void getReplies() {
    Log.d(TAG, "getReplies");
    if (status == 0) {
      status = 1;
      ((TopicDataNetwork) data).getReplies(id, null, null);
    }
  }

  public void addReplies(Integer offset) {
    Log.d(TAG, "addReplies");
    if (status == 0) {
      status = 2;
      ((TopicDataNetwork) data).getReplies(id, offset, null);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showReplies(TopicRepliesEvent topicRepliesEvent) {
    Log.d(TAG, "showReplies");
    if (status == 1) {
      status = 0;
      topicRepliesView.showReplies(topicRepliesEvent.getTopicReplyList());
    } else if (status == 2) {
      status = 0;
      topicRepliesView.addReplies(topicRepliesEvent.getTopicReplyList());
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void getNewTopicReply(CreateTopicReplyEvent createTopicReplyEvent) {
    Log.d(TAG, "getNewTopicReply");
    if (createTopicReplyEvent.isSuccessful()) {
      topicRepliesView.showNewReply();
    }
    EventBus.getDefault().removeStickyEvent(createTopicReplyEvent);
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
