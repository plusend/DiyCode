package com.plusend.diycode.mvp.presenter;

import com.plusend.diycode.event.NewsEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.NewsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/12/2.
 */

public class NewsPresenter extends Presenter {
  private static final String TAG = "NewsPresenter";

  private NewsView newsView;
  private Data data;

  public NewsPresenter(NewsView newsView) {
    this.newsView = newsView;
    this.data = NetworkData.getInstance();
  }

  public void readNews(int offset) {
    data.readNews(null, offset, null);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showNews(NewsEvent event) {
    newsView.showNews(event.getNewsList());
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
