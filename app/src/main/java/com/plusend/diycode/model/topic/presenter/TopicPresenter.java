package com.plusend.diycode.model.topic.presenter;

import android.util.Log;
import com.plusend.diycode.model.base.BaseData;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.data.TopicDataNetwork;
import com.plusend.diycode.model.topic.event.CreateTopicEvent;
import com.plusend.diycode.model.topic.event.FavoriteEvent;
import com.plusend.diycode.model.topic.event.FollowEvent;
import com.plusend.diycode.model.topic.event.LoadTopicDetailFinishEvent;
import com.plusend.diycode.model.topic.event.SignInEvent;
import com.plusend.diycode.model.topic.event.TopicDetailEvent;
import com.plusend.diycode.model.topic.event.UnFavoriteEvent;
import com.plusend.diycode.model.topic.event.UnFollowEvent;
import com.plusend.diycode.model.topic.view.TopicView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicPresenter extends BasePresenter {
    private static final String TAG = "TopicPresenter";
    private static final String LIKE_OBJ_TYPE_TOPIC = "topic";
    private BaseData data;
    private TopicView topicView;

    public TopicPresenter(TopicView topicView) {
        this.data = TopicDataNetwork.getInstance();
        this.topicView = topicView;
    }

    public void getTopic(int id) {
        ((TopicDataNetwork) data).getTopic(id);
    }

    public void favoriteTopic(int id) {
        ((TopicDataNetwork) data).favorite(id);
    }

    public void unFavoriteTopic(int id) {
        ((TopicDataNetwork) data).unFavorite(id);
    }

    public void followTopic(int id) {
        ((TopicDataNetwork) data).follow(id);
    }

    public void unFollowTopic(int id) {
        ((TopicDataNetwork) data).unFollow(id);
    }

    public void like(Integer id) {
        ((TopicDataNetwork) data).like(LIKE_OBJ_TYPE_TOPIC, id);
    }

    public void unLike(Integer id) {
        ((TopicDataNetwork) data).unLike(LIKE_OBJ_TYPE_TOPIC, id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showTopic(TopicDetailEvent topicDetailEvent) {
        Log.d(TAG, "showTopic");
        topicView.showTopic(topicDetailEvent.getTopicDetail());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadTopicFinish(LoadTopicDetailFinishEvent event) {
        Log.d(TAG, "loadTopicFinish");
        topicView.loadTopicFinish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void showNewTopic(CreateTopicEvent createTopicEvent) {
        Log.d(TAG, "showNewTopic");
        topicView.showTopic(createTopicEvent.getTopicDetail());
        EventBus.getDefault().removeStickyEvent(createTopicEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showFavorite(FavoriteEvent event) {
        topicView.showFavorite(event.isResult());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showUnFavorite(UnFavoriteEvent event) {
        topicView.showUnFavorite(event.isResult());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showFollow(FollowEvent event) {
        topicView.showFollow(event.isResult());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showUnFollow(UnFollowEvent event) {
        topicView.showUnFollow(event.isResult());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void showSignIn(SignInEvent event) {
        topicView.showSignIn();
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
