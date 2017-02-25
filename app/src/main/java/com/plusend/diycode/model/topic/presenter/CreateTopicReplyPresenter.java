package com.plusend.diycode.model.topic.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.model.topic.event.CreateTopicReplyEvent;
import com.plusend.diycode.model.topic.view.CreateTopicReplyView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateTopicReplyPresenter extends BasePresenter {
  private static final String TAG = "CreateTopicReplyPresent";
  private CreateTopicReplyView createTopicReplyView;
  private BaseData data;

  public CreateTopicReplyPresenter(CreateTopicReplyView createTopicReplyView) {
    this.createTopicReplyView = createTopicReplyView;
    data = TopicDataNetwork.getInstance();
  }

  public void createTopicReply(int id, String body) {
    ((TopicDataNetwork) data).createReply(id, body);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void getResult(CreateTopicReplyEvent createTopicReplyEvent) {
    Log.d(TAG, "getResult");
    createTopicReplyView.getResult(createTopicReplyEvent.isSuccessful());
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
