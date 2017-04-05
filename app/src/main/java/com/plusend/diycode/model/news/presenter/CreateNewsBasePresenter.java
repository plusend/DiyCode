package com.plusend.diycode.model.news.presenter;

import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.news.data.NewsDataNetwork;
import com.plusend.diycode.model.news.event.CreateNewsEvent;
import com.plusend.diycode.model.news.view.CreateNewsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateNewsBasePresenter extends BasePresenter {
    private BaseData mData;
    private CreateNewsView mView;

    public CreateNewsBasePresenter(CreateNewsView createNewsView) {
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
