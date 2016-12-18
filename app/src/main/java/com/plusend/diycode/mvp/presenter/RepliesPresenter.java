package com.plusend.diycode.mvp.presenter;

import com.plusend.diycode.event.RepliesEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.RepliesView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/12/18.
 */

public class RepliesPresenter extends Presenter {

  private RepliesView repliesView;
  private Data data;

  public RepliesPresenter(RepliesView repliesView) {
    this.repliesView = repliesView;
    data = NetworkData.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showReplies(RepliesEvent event) {
    repliesView.showReplies(event.getReplyList());
  }

  public void getReplies(String loginName, Integer offset) {
    data.getUserReplies(loginName, offset, null);
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
