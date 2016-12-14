package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.TopicRepliesEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.TopicRepliesView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/26.
 */

public class TopicRepliesPresenter extends Presenter {
  private static final String TAG = "TopicRepliesPresenter";
  private TopicRepliesView topicRepliesView;
  private Data data;
  private int id;
  private int status;

  public TopicRepliesPresenter(TopicRepliesView topicRepliesView, int id) {
    this.topicRepliesView = topicRepliesView;
    this.data = NetworkData.getInstance();
    this.id = id;
  }

  public void getReplies() {
    Log.d(TAG, "getReplies");
    if (status == 0) {
      status = 1;
      data.getReplies(id, null, null);
    }
  }

  public void addReplies(Integer offset) {
    Log.d(TAG, "addReplies");
    if (status == 0) {
      status = 2;
      data.getReplies(id, offset, null);
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

  @Override public void start() {
    Log.d(TAG, "register");
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    Log.d(TAG, "unregister");
    EventBus.getDefault().unregister(this);
  }
}
