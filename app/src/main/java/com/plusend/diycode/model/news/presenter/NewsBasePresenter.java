package com.plusend.diycode.model.news.presenter;

import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.news.data.NewsDataNetwork;
import com.plusend.diycode.model.news.event.NewsEvent;
import com.plusend.diycode.model.news.view.NewsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewsBasePresenter extends BasePresenter {
    private static final String TAG = "NewsPresenter";

    private NewsView newsView;
    private BaseData data;

    public NewsBasePresenter(NewsView newsView) {
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
