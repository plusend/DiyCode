package com.plusend.diycode.mvp.model.network;

import android.util.Log;
import com.plusend.diycode.event.CreateTopicReplyEvent;
import com.plusend.diycode.event.FavoriteEvent;
import com.plusend.diycode.event.FollowEvent;
import com.plusend.diycode.event.MeEvent;
import com.plusend.diycode.event.NewTopicEvent;
import com.plusend.diycode.event.NewsEvent;
import com.plusend.diycode.event.NodesEvent;
import com.plusend.diycode.event.NotificationDeleteEvent;
import com.plusend.diycode.event.NotificationsEvent;
import com.plusend.diycode.event.RepliesEvent;
import com.plusend.diycode.event.SiteEvent;
import com.plusend.diycode.event.TokenEvent;
import com.plusend.diycode.event.TopicDetailEvent;
import com.plusend.diycode.event.TopicRepliesEvent;
import com.plusend.diycode.event.TopicsEvent;
import com.plusend.diycode.event.UnFavoriteEvent;
import com.plusend.diycode.event.UnFollowEvent;
import com.plusend.diycode.event.UserDetailInfoEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.entity.FavoriteTopic;
import com.plusend.diycode.mvp.model.entity.FollowTopic;
import com.plusend.diycode.mvp.model.entity.News;
import com.plusend.diycode.mvp.model.entity.Node;
import com.plusend.diycode.mvp.model.entity.Notification;
import com.plusend.diycode.mvp.model.entity.NotificationDelete;
import com.plusend.diycode.mvp.model.entity.Reply;
import com.plusend.diycode.mvp.model.entity.Site;
import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.model.entity.Topic;
import com.plusend.diycode.mvp.model.entity.TopicDetail;
import com.plusend.diycode.mvp.model.entity.TopicReply;
import com.plusend.diycode.mvp.model.entity.UnFavoriteTopic;
import com.plusend.diycode.mvp.model.entity.UnFollowTopic;
import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;
import com.plusend.diycode.mvp.model.user.network.UserInfoService;
import com.plusend.diycode.util.Constant;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetworkData implements Data {
  private static final String TAG = "NetworkData";
  private DiyCodeService service;
  private UserInfoService userInfoService;
  private static NetworkData networkData = new NetworkData();

  private NetworkData() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    service = retrofit.create(DiyCodeService.class);
    userInfoService = retrofit.create(UserInfoService.class);
  }

  public static NetworkData getInstance() {
    return networkData;
  }

  @Override public void getToken(String username, String password) {
    Call<Token> call = service.getToken(Constant.VALUE_CLIENT_ID, Constant.VALUE_CLIENT_SECRET,
        Constant.VALUE_GRANT_TYPE, username, password);
    call.enqueue(new Callback<Token>() {
      @Override public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
        if (response.isSuccessful()) {
          Token token = response.body();
          //Log.d(TAG, "token: " + token);
          EventBus.getDefault().post(new TokenEvent(token));
        } else {
          EventBus.getDefault().post(new TokenEvent(null));
          Log.e(TAG, "getToken STATUS: " + response.code());
        }
      }

      @Override public void onFailure(Call<Token> call, Throwable t) {
        Log.d(TAG, t.getMessage());
        EventBus.getDefault().post(new TokenEvent(null));
      }
    });
  }

  @Override public void getMe() {
    Call<UserDetailInfo> call = service.getMe(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN);
    call.enqueue(new Callback<UserDetailInfo>() {
      @Override public void onResponse(Call<UserDetailInfo> call,
          retrofit2.Response<UserDetailInfo> response) {
        if (response.isSuccessful()) {
          UserDetailInfo userDetailInfo = response.body();
          Log.d(TAG, "user: " + userDetailInfo);
          EventBus.getDefault().post(new MeEvent(userDetailInfo));
        } else {
          Log.e(TAG, "getMe STATUS: " + response.code());
          EventBus.getDefault().post(new MeEvent(null));
        }
      }

      @Override public void onFailure(Call<UserDetailInfo> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new MeEvent(null));
      }
    });
  }

  @Override public void getUser(String loginName) {
    Call<UserDetailInfo> call =
        service.getUser(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName);
    call.enqueue(new Callback<UserDetailInfo>() {
      @Override public void onResponse(Call<UserDetailInfo> call,
          retrofit2.Response<UserDetailInfo> response) {
        if (response.isSuccessful()) {
          UserDetailInfo userDetailInfo = response.body();
          Log.d(TAG, "user: " + userDetailInfo);
          EventBus.getDefault().post(new UserDetailInfoEvent(userDetailInfo));
        } else {
          Log.e(TAG, "getUser STATUS: " + response.code());
          EventBus.getDefault().post(new UserDetailInfoEvent(null));
        }
      }

      @Override public void onFailure(Call<UserDetailInfo> call, Throwable t) {
        Log.e(TAG, t.getMessage());
      }
    });
  }

  @Override public void getTopics(String type, Integer nodeId, Integer offset, Integer limit) {
    Call<List<Topic>> call = service.getTopics(type, nodeId, offset, limit);
    call.enqueue(new Callback<List<Topic>>() {
      @Override
      public void onResponse(Call<List<Topic>> call, retrofit2.Response<List<Topic>> response) {
        if (response.isSuccessful()) {
          List<Topic> topicList = response.body();
          Log.v(TAG, "topicList:" + topicList);
          EventBus.getDefault().post(new TopicsEvent(topicList));
        } else {
          Log.e(TAG, "getTopics STATUS: " + response.code());
          EventBus.getDefault().post(new TopicsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new TopicsEvent(null));
      }
    });
  }

  @Override public void getTopic(int id) {
    Call<TopicDetail> call = service.getTopic(id);
    call.enqueue(new Callback<TopicDetail>() {
      @Override
      public void onResponse(Call<TopicDetail> call, retrofit2.Response<TopicDetail> response) {
        if (response.isSuccessful()) {
          TopicDetail topicDetail = response.body();
          Log.v(TAG, "getTopic topicDetail:" + topicDetail);
          EventBus.getDefault().post(new TopicDetailEvent(topicDetail));
          //Map<String, List<String>> map = response.headers().toMultimap();
          //for (Map.Entry<String, List<String>> entry : map.entrySet()) {
          //  Log.d(TAG, "Key : " + entry.getKey() + " ,Value : " + entry.getValue());
          //}
        } else {
          Log.e(TAG, "getTopic STATUS: " + response.code());
          EventBus.getDefault().post(new TopicDetailEvent(null));
        }
      }

      @Override public void onFailure(Call<TopicDetail> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new TopicDetailEvent(null));
      }
    });
  }

  @Override public void getReplies(int id, Integer offset, Integer limit) {
    Call<List<TopicReply>> call = service.getReplies(id, offset, limit);
    call.enqueue(new Callback<List<TopicReply>>() {
      @Override public void onResponse(Call<List<TopicReply>> call,
          retrofit2.Response<List<TopicReply>> response) {
        if (response.isSuccessful()) {
          List<TopicReply> topicReplyList = response.body();
          Log.v(TAG, "topicReplyList:" + topicReplyList);
          EventBus.getDefault().post(new TopicRepliesEvent(topicReplyList));
        } else {
          Log.e(TAG, "getReplies STATUS: " + response.code());
          EventBus.getDefault().post(new TopicRepliesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<TopicReply>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new TopicRepliesEvent(null));
      }
    });
  }

  @Override public void getSite() {
    Call<List<Site>> call = service.getSite();

    call.enqueue(new Callback<List<Site>>() {
      @Override
      public void onResponse(Call<List<Site>> call, retrofit2.Response<List<Site>> response) {
        if (response.isSuccessful()) {
          List<Site> siteList = response.body();
          Log.v(TAG, "siteList:" + siteList);
          EventBus.getDefault().post(new SiteEvent(siteList));
        } else {
          Log.e(TAG, "getSite STATUS: " + response.code());
          EventBus.getDefault().post(new SiteEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Site>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new SiteEvent(null));
      }
    });
  }

  @Override public void getUserCreateTopics(String loginName, Integer offset, Integer limit) {
    Call<List<Topic>> call = service.getUserCreateTopics(loginName, offset, limit);

    call.enqueue(new Callback<List<Topic>>() {
      @Override public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
        if (response.isSuccessful()) {
          List<Topic> topicList = response.body();
          Log.v(TAG, "topicList:" + topicList);
          EventBus.getDefault().post(new TopicsEvent(topicList));
        } else {
          Log.e(TAG, "getUserCreateTopics STATUS: " + response.code());
          EventBus.getDefault().post(new TopicsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new TopicsEvent(null));
      }
    });
  }

  @Override public void getUserFavoriteTopics(String loginName, Integer offset, Integer limit) {
    Call<List<Topic>> call = service.getUserFavoriteTopics(loginName, offset, limit);

    call.enqueue(new Callback<List<Topic>>() {
      @Override public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
        if (response.isSuccessful()) {
          List<Topic> topicList = response.body();
          Log.v(TAG, "topicList:" + topicList);
          EventBus.getDefault().post(new TopicsEvent(topicList));
        } else {
          Log.e(TAG, "getUserFavoriteTopics STATUS: " + response.code());
          EventBus.getDefault().post(new TopicsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new TopicsEvent(null));
      }
    });
  }

  @Override public void getUserReplies(String loginName, Integer offset, Integer limit) {
    Call<List<Reply>> call = service.getUserReplies(loginName, offset, limit);
    call.enqueue(new Callback<List<Reply>>() {
      @Override public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
        if (response.isSuccessful()) {
          List<Reply> replyList = response.body();
          Log.v(TAG, "replyList: " + replyList);
          EventBus.getDefault().post(new RepliesEvent(replyList));
        } else {
          Log.e(TAG, "getUserReplies STATUS: " + response.code());
          EventBus.getDefault().post(new RepliesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Reply>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new RepliesEvent(null));
      }
    });
  }

  @Override public void newTopic(final String title, String body, int nodeId) {
    Call<TopicDetail> call =
        service.newTopic(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, title, body, nodeId);
    call.enqueue(new Callback<TopicDetail>() {
      @Override public void onResponse(Call<TopicDetail> call, Response<TopicDetail> response) {
        if (response.isSuccessful()) {
          TopicDetail topicDetail = response.body();
          Log.v(TAG, "topicDetail: " + topicDetail);
          EventBus.getDefault().postSticky(new NewTopicEvent(topicDetail));
          EventBus.getDefault().post(new NewTopicEvent(topicDetail));
        } else {
          Log.e(TAG, "newTopic STATUS: " + response.code());
          EventBus.getDefault().post(new NewTopicEvent(null));
        }
      }

      @Override public void onFailure(Call<TopicDetail> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new NewTopicEvent(null));
      }
    });
  }

  @Override public void favorite(int id) {
    Call<FavoriteTopic> call =
        service.favorite(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, id);
    call.enqueue(new Callback<FavoriteTopic>() {
      @Override public void onResponse(Call<FavoriteTopic> call, Response<FavoriteTopic> response) {
        if (response.isSuccessful()) {
          FavoriteTopic favoriteTopic = response.body();
          Log.v(TAG, "favorite: " + favoriteTopic);
          EventBus.getDefault().post(new FavoriteEvent(favoriteTopic.getOk() == 1));
        } else {
          Log.e(TAG, "favorite STATUS: " + response.code());
          EventBus.getDefault().post(new FavoriteEvent(false));
        }
      }

      @Override public void onFailure(Call<FavoriteTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new FavoriteEvent(false));
      }
    });
  }

  @Override public void unFavorite(int id) {
    Call<UnFavoriteTopic> call =
        service.unFavorite(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, id);
    call.enqueue(new Callback<UnFavoriteTopic>() {
      @Override
      public void onResponse(Call<UnFavoriteTopic> call, Response<UnFavoriteTopic> response) {
        if (response.isSuccessful()) {
          UnFavoriteTopic unFavoriteTopic = response.body();
          Log.v(TAG, "unFavorite: " + unFavoriteTopic);
          EventBus.getDefault().post(new UnFavoriteEvent(unFavoriteTopic.getOk() == 1));
        } else {
          Log.e(TAG, "unFavorite STATUS: " + response.code());
          EventBus.getDefault().post(new UnFavoriteEvent(false));
        }
      }

      @Override public void onFailure(Call<UnFavoriteTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UnFavoriteEvent(false));
      }
    });
  }

  @Override public void follow(int id) {
    Call<FollowTopic> call = service.follow(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, id);
    call.enqueue(new Callback<FollowTopic>() {
      @Override public void onResponse(Call<FollowTopic> call, Response<FollowTopic> response) {
        if (response.isSuccessful()) {
          FollowTopic followTopic = response.body();
          Log.v(TAG, "follow: " + followTopic);
          EventBus.getDefault().post(new FollowEvent(followTopic.getOk() == 1));
        } else {
          Log.e(TAG, "follow STATUS: " + response.code());
          EventBus.getDefault().post(new FollowEvent(false));
        }
      }

      @Override public void onFailure(Call<FollowTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new FollowEvent(false));
      }
    });
  }

  @Override public void unFollow(int id) {
    Call<UnFollowTopic> call =
        service.unFollow(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, id);
    call.enqueue(new Callback<UnFollowTopic>() {
      @Override public void onResponse(Call<UnFollowTopic> call, Response<UnFollowTopic> response) {
        if (response.isSuccessful()) {
          UnFollowTopic unFollowTopic = response.body();
          Log.v(TAG, "unFollow: " + unFollowTopic);
          EventBus.getDefault().post(new UnFollowEvent(unFollowTopic.getOk() == 1));
        } else {
          Log.e(TAG, "unFollow STATUS: " + response.code());
          EventBus.getDefault().post(new UnFollowEvent(false));
        }
      }

      @Override public void onFailure(Call<UnFollowTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UnFollowEvent(false));
      }
    });
  }

  @Override public void createReply(int id, String body) {
    Call<TopicReply> call =
        service.createReply(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, id, body);
    call.enqueue(new Callback<TopicReply>() {
      @Override public void onResponse(Call<TopicReply> call, Response<TopicReply> response) {
        if (response.isSuccessful()) {
          TopicReply topicReply = response.body();
          Log.v(TAG, "topicReply:" + topicReply);
          EventBus.getDefault().postSticky(new CreateTopicReplyEvent(true));
        } else {
          Log.e(TAG, "createReply STATUS: " + response.code());
          EventBus.getDefault().post(new CreateTopicReplyEvent(false));
        }
      }

      @Override public void onFailure(Call<TopicReply> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new CreateTopicReplyEvent(false));
      }
    });
  }

  @Override public void readNews(Integer nodeId, Integer offset, Integer limit) {
    Call<List<News>> call = service.readNews(nodeId, offset, limit);
    call.enqueue(new Callback<List<News>>() {
      @Override public void onResponse(Call<List<News>> call, Response<List<News>> response) {
        if (response.isSuccessful()) {
          List<News> newsList = response.body();
          Log.v(TAG, "newsList:" + newsList);
          EventBus.getDefault().post(new NewsEvent(newsList));
        } else {
          Log.e(TAG, "readNews STATUS: " + response.code());
          EventBus.getDefault().post(new NewsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<News>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new NewsEvent(null));
      }
    });
  }

  @Override public void readNodes() {
    Call<List<Node>> call = service.readNodes();
    call.enqueue(new Callback<List<Node>>() {
      @Override public void onResponse(Call<List<Node>> call, Response<List<Node>> response) {
        if (response.isSuccessful()) {
          List<Node> nodeList = response.body();
          Log.v(TAG, "nodeList:" + nodeList);
          EventBus.getDefault().post(new NodesEvent(nodeList));
        } else {
          Log.e(TAG, "readNodes STATUS: " + response.code());
          EventBus.getDefault().post(new NodesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Node>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new NodesEvent(null));
      }
    });
  }

  @Override public void readNotifications(Integer offset, Integer limit) {
    Call<List<Notification>> call =
        service.readNotifications(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, offset,
            limit);
    call.enqueue(new Callback<List<Notification>>() {
      @Override
      public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
        if (response.isSuccessful()) {
          List<Notification> notificationList = response.body();
          Log.v(TAG, "notificationList: " + notificationList);
          EventBus.getDefault().postSticky(new NotificationsEvent(notificationList));
        } else {
          Log.e(TAG, "readNotifications STATUS: " + response.code());
          EventBus.getDefault().postSticky(new NotificationsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Notification>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new NotificationsEvent(null));
      }
    });
  }

  @Override public void deleteNotification(int id) {
    Call<NotificationDelete> call =
        service.deleteNotification(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, id);
    call.enqueue(new Callback<NotificationDelete>() {
      @Override
      public void onResponse(Call<NotificationDelete> call, Response<NotificationDelete> response) {
        if (response.isSuccessful()) {
          NotificationDelete notificationDelete = response.body();
          Log.v(TAG, "notificationDelete: " + notificationDelete);
          EventBus.getDefault().post(new NotificationDeleteEvent(notificationDelete));
        } else {
          Log.e(TAG, "deleteNotification STATUS: " + response.code());
          EventBus.getDefault().post(new NotificationDeleteEvent(null));
        }
      }

      @Override public void onFailure(Call<NotificationDelete> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new NotificationDeleteEvent(null));
      }
    });
  }

  @Override public void deleteAllNotifications() {

  }

  @Override public void markNotificationsAsRead(int[] ids) {

  }

  @Override public void unReadNotificationCount() {

  }
}
