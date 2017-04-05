package com.plusend.diycode.model.topic.presenter;

import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.model.topic.event.CreateTopicEvent;
import com.plusend.diycode.model.topic.view.CreateTopicView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateTopicPresenter extends BasePresenter {
    private BaseData data;
    private CreateTopicView createTopicView;

    public CreateTopicPresenter(CreateTopicView createTopicView) {
        this.createTopicView = createTopicView;
        this.data = TopicDataNetwork.getInstance();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNewTopic(CreateTopicEvent createTopicEvent) {
        createTopicView.getNewTopic(createTopicEvent.getTopicDetail());
    }

    public void newTopic(String title, String body, int node_id) {
        ((TopicDataNetwork) data).newTopic(title, body, node_id);
    }

    @Override public void start() {
        EventBus.getDefault().register(this);
    }

    @Override public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
