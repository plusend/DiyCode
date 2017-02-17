package com.plusend.diycode.mvp.model.topic.presenter;

import android.util.Log;
import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.topic.event.RepliesEvent;
import com.plusend.diycode.mvp.model.topic.view.RepliesView;
import com.plusend.diycode.mvp.model.user.model.UserDataNetwork;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RepliesPresenter extends Presenter {
  private static final String TAG = "RepliesPresenter";
  private RepliesView repliesView;
  private BaseData data;

  public RepliesPresenter(RepliesView repliesView) {
    this.repliesView = repliesView;
    data = UserDataNetwork.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void showReplies(RepliesEvent event) {
    Log.d(TAG, "showReplies");
    repliesView.showReplies(event.getReplyList());
    EventBus.getDefault().removeStickyEvent(event);
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
