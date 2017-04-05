package com.plusend.diycode.model.news.node.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.news.node.data.NewsNodeDataNetwork;
import com.plusend.diycode.model.news.node.event.NewsNodesEvent;
import com.plusend.diycode.model.news.node.view.NewsNodesView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewsNodesBasePresenter extends BasePresenter {
    private static final String TAG = "NodesPresenter";
    private NewsNodesView newsNodesView;
    private BaseData data;

    public NewsNodesBasePresenter(NewsNodesView newsNodesView) {
        this.newsNodesView = newsNodesView;
        data = NewsNodeDataNetwork.getInstance();
    }

    public void readNodes() {
        ((NewsNodeDataNetwork) data).readNewsNodes();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void showNewsNodes(NewsNodesEvent newsNodesEvent) {
        Log.d(TAG, "showNewsNodes");
        newsNodesView.showNodes(newsNodesEvent.getNewsNodeList());
        EventBus.getDefault().removeStickyEvent(newsNodesEvent);
    }

    @Override public void start() {
        EventBus.getDefault().register(this);
    }

    @Override public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
