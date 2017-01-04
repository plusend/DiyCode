package com.plusend.diycode.mvp.model.topic.presenter;

import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.mvp.model.topic.event.RepliesEvent;
import com.plusend.diycode.mvp.model.topic.view.RepliesView;
import com.plusend.diycode.mvp.model.user.model.UserDataNetwork;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RepliesPresenter extends Presenter {

  private RepliesView repliesView;
  private BaseData data;

  public RepliesPresenter(RepliesView repliesView) {
    this.repliesView = repliesView;
    data = UserDataNetwork.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showReplies(RepliesEvent event) {
    repliesView.showReplies(event.getReplyList());
  }

  public void getReplies(String loginName, Integer offset) {
    ((UserDataNetwork) data).getUserReplies(loginName, offset, null);
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
