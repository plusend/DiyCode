package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.FavoriteEvent;
import com.plusend.diycode.event.FollowEvent;
import com.plusend.diycode.event.UnFavoriteEvent;
import com.plusend.diycode.event.UnFollowEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.FollowView;
import com.plusend.diycode.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/12/7.
 */

public class FollowPresenter extends Presenter {
  private static final String TAG = "FollowPresenter";

  private FollowView followView;
  private Data data;
  private int id;

  public FollowPresenter(FollowView followView, int id) {
    this.followView = followView;
    this.id = id;
    this.data = NetworkData.getInstance();
  }

  public void setFollow(boolean isFollowed) {
    Log.d(TAG, "setFollow: " + isFollowed);
    if (isFollowed) {
      data.follow(id);
    } else {
      data.unFollow(id);
    }
  }

  public void serFavorite(boolean isFavorite) {
    Log.d(TAG, "serFavorite: " + isFavorite);
    if (isFavorite) {
      data.favorite(id);
    } else {
      data.unFavorite(id);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getResult(FollowEvent followEvent) {
    if (followEvent.isResult()) {
      followView.setFollow(true);
    } else {
      ToastUtil.showText(followView.getContext(), "关注失败");
      followView.setFollow(false);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getResult(UnFollowEvent unFollowEvent) {
    if (unFollowEvent.isResult()) {
      followView.setFollow(false);
    } else {
      ToastUtil.showText(followView.getContext(), "取消关注失败");
      followView.setFollow(true);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getResult(FavoriteEvent favoriteEvent) {
    if (favoriteEvent.isResult()) {
      followView.setFavorite(true);
    } else {
      ToastUtil.showText(followView.getContext(), "收藏失败");
      followView.setFavorite(false);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getResult(UnFavoriteEvent unFavoriteEvent) {
    if (unFavoriteEvent.isResult()) {
      followView.setFavorite(false);
    } else {
      ToastUtil.showText(followView.getContext(), "取消收藏失败");
      followView.setFavorite(true);
    }
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
