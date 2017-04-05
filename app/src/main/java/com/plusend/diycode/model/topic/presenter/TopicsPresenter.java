package com.plusend.diycode.model.topic.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.model.topic.event.TopTopicsEvent;
import com.plusend.diycode.model.topic.event.TopicsEvent;
import com.plusend.diycode.model.topic.view.TopicsView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicsPresenter extends BasePresenter {
    private static final String TAG = "TopicsPresenter";

    private BaseData data;
    private TopicsView topicsView;

    public TopicsPresenter(TopicsView topicsView) {
        this.data = TopicDataNetwork.getInstance();
        this.topicsView = topicsView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showTopics(TopicsEvent topicsEvent) {
        Log.d(TAG, "showTopics: " + topicsEvent.getTopicList());
        topicsView.showTopics(topicsEvent.getTopicList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showTopTopics(TopTopicsEvent topicsEvent) {
        Log.d(TAG, "showTopTopics: " + topicsEvent.getTopicList());
        topicsView.showTopTopics(topicsEvent.getTopicList());
    }

    public void getTopics(Integer offset) {
        Log.d(TAG, "getTopics: offset: " + offset);
        ((TopicDataNetwork) data).getTopics(null, null, offset, null);
    }

    public void getTopTopics() {
        Log.d(TAG, "getTopTopics");
        ((TopicDataNetwork) data).getTopTopics();
    }

    @Override public void start() {
        Log.d(TAG, "register");
        EventBus.getDefault().register(this);
    }

    @Override public void stop() {
        Log.d(TAG, "unregister");
        EventBus.getDefault().unregister(this);
    }
}
