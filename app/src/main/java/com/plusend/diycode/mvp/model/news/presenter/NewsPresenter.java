package com.plusend.diycode.mvp.model.news.presenter;

import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.news.data.NewsDataNetwork;
import com.plusend.diycode.mvp.model.news.event.NewsEvent;
import com.plusend.diycode.mvp.model.news.view.NewsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewsPresenter extends Presenter {
  private static final String TAG = "NewsPresenter";

  private NewsView newsView;
  private BaseData data;

  public NewsPresenter(NewsView newsView) {
    this.newsView = newsView;
    this.data = NewsDataNetwork.getInstance();
  }

  public void readNews(int offset) {
    ((NewsDataNetwork) data).readNews(null, offset, null);
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
