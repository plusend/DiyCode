package com.plusend.diycode.mvp.model.news.presenter;

import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.BaseView;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.news.data.NewsData;
import com.plusend.diycode.mvp.model.news.data.NewsDataNetwork;
import com.plusend.diycode.mvp.model.news.event.CreateNewsEvent;
import com.plusend.diycode.mvp.model.news.view.CreateNewsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateNewsPresenter extends Presenter {
  private BaseData mData;
  private CreateNewsView mView;

  public CreateNewsPresenter(CreateNewsView createNewsView) {
    this.mView = createNewsView;
    mData = NewsDataNetwork.getInstance();
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }

  public void createNews(String title, String address, Integer node_id) {
    ((NewsDataNetwork) mData).createNews(title, address, node_id);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showNews(CreateNewsEvent event) {
    mView.showNews(event.getNews());
  }
}
