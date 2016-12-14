package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.CreateTopicReplyEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.CreateTopicReplyView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/12/1.
 */

public class CreateTopicReplyPresenter extends Presenter {
  private static final String TAG = "CreateTopicReplyPresent";
  private CreateTopicReplyView createTopicReplyView;
  private Data data;

  public CreateTopicReplyPresenter(CreateTopicReplyView createTopicReplyView) {
    this.createTopicReplyView = createTopicReplyView;
    data = NetworkData.getInstance();
  }

  public void createTopicReply(int id, String body) {
    data.createReply(id, body);
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
