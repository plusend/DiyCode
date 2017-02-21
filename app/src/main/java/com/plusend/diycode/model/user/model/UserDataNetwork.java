package com.plusend.diycode.model.user.model;

import android.util.Log;
import com.plusend.diycode.model.user.entity.Token;
import com.plusend.diycode.model.topic.entity.Reply;
import com.plusend.diycode.model.topic.event.RepliesEvent;
import com.plusend.diycode.model.user.event.UserTopicsEvent;
import com.plusend.diycode.model.user.entity.UserFollow;
import com.plusend.diycode.model.user.entity.UserUnFollow;
import com.plusend.diycode.model.user.event.MeEvent;
import com.plusend.diycode.model.user.event.TokenEvent;
import com.plusend.diycode.model.user.event.UserBlockEvent;
import com.plusend.diycode.model.user.event.UserBlockedEvent;
import com.plusend.diycode.model.user.event.UserDetailInfoEvent;
import com.plusend.diycode.model.user.event.UserFavoriteTopicsEvent;
import com.plusend.diycode.model.user.event.UserFollowEvent;
import com.plusend.diycode.model.user.event.UserFollowersEvent;
import com.plusend.diycode.model.user.event.UserFollowingEvent;
import com.plusend.diycode.model.user.event.UserUnBlockEvent;
import com.plusend.diycode.model.topic.entity.Topic;
import com.plusend.diycode.model.user.entity.UserBlock;
import com.plusend.diycode.model.user.entity.UserUnBlock;
import com.plusend.diycode.model.user.entity.UserDetailInfo;
import com.plusend.diycode.model.user.entity.UserInfo;
import com.plusend.diycode.model.user.event.UserUnFollowEvent;
import com.plusend.diycode.util.Constant;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserDataNetwork implements UserData {
  private static final String TAG = "UserNetworkData";

  private UserService userService;
  private static UserDataNetwork userDataNetwork = new UserDataNetwork();

  private UserDataNetwork() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    userService = retrofit.create(UserService.class);
  }

  public static UserDataNetwork getInstance() {
    return userDataNetwork;
  }

  @Override public void getToken(String username, String password) {
    Call<Token> call = userService.getToken(Constant.VALUE_CLIENT_ID, Constant.VALUE_CLIENT_SECRET,
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
    Call<UserDetailInfo> call =
        userService.getMe(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN);
    call.enqueue(new Callback<UserDetailInfo>() {
      @Override public void onResponse(Call<UserDetailInfo> call,
          retrofit2.Response<UserDetailInfo> response) {
        if (response.isSuccessful()) {
          UserDetailInfo userDetailInfo = response.body();
          Log.d(TAG, "userDetailInfo: " + userDetailInfo);
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
        userService.getUser(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName);
    call.enqueue(new Callback<UserDetailInfo>() {
      @Override public void onResponse(Call<UserDetailInfo> call,
          retrofit2.Response<UserDetailInfo> response) {
        if (response.isSuccessful()) {
          UserDetailInfo userDetailInfo = response.body();
          Log.d(TAG, "user: " + userDetailInfo);
          EventBus.getDefault().postSticky(new UserDetailInfoEvent(userDetailInfo));
        } else {
          Log.e(TAG, "getUser STATUS: " + response.code());
          EventBus.getDefault().postSticky(new UserDetailInfoEvent(null));
        }
      }

      @Override public void onFailure(Call<UserDetailInfo> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new UserDetailInfoEvent(null));
      }
    });
  }

  @Override
  public void getUserBlocked(String token, String loginName, Integer offset, Integer limit) {
    Call<List<UserInfo>> call =
        userService.getUserBlocked(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName,
            offset, limit);
    call.enqueue(new Callback<List<UserInfo>>() {
      @Override
      public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
        if (response.isSuccessful()) {
          List<UserInfo> userInfoList = response.body();
          Log.v(TAG, "userInfoList: " + userInfoList);
          EventBus.getDefault().post(new UserBlockedEvent(userInfoList));
        } else {
          Log.e(TAG, "getUserBlocked STATUS: " + response.code());
          EventBus.getDefault().post(new UserBlockedEvent(null));
        }
      }

      @Override public void onFailure(Call<List<UserInfo>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserBlockedEvent(null));
      }
    });
  }

  @Override
  public void getUserFollowing(String token, String loginName, Integer offset, Integer limit) {
    Call<List<UserInfo>> call =
        userService.getUserFollowing(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName,
            offset, limit);
    call.enqueue(new Callback<List<UserInfo>>() {
      @Override
      public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
        if (response.isSuccessful()) {
          List<UserInfo> userInfoList = response.body();
          Log.v(TAG, "userInfoList: " + userInfoList);
          EventBus.getDefault().post(new UserFollowingEvent(userInfoList));
        } else {
          Log.e(TAG, "getUserBlocked STATUS: " + response.code());
          EventBus.getDefault().post(new UserFollowingEvent(null));
        }
      }

      @Override public void onFailure(Call<List<UserInfo>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserFollowingEvent(null));
      }
    });
  }

  @Override
  public void getUserFollowers(String token, String loginName, Integer offset, Integer limit) {
    Call<List<UserInfo>> call =
        userService.getUserFollowers(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName,
            offset, limit);
    call.enqueue(new Callback<List<UserInfo>>() {
      @Override
      public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
        if (response.isSuccessful()) {
          List<UserInfo> userInfoList = response.body();
          Log.v(TAG, "userInfoList: " + userInfoList);
          EventBus.getDefault().post(new UserFollowersEvent(userInfoList));
        } else {
          Log.e(TAG, "getUserBlocked STATUS: " + response.code());
          EventBus.getDefault().post(new UserFollowersEvent(null));
        }
      }

      @Override public void onFailure(Call<List<UserInfo>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserFollowersEvent(null));
      }
    });
  }

  @Override public void blockUser(String token, String loginName) {
    Call<UserBlock> call = userService.blockUser(token, loginName);
    call.enqueue(new Callback<UserBlock>() {
      @Override public void onResponse(Call<UserBlock> call, Response<UserBlock> response) {
        if (response.isSuccessful()) {
          UserBlock userBlock = response.body();
          Log.v(TAG, "blockUser: " + userBlock);
          EventBus.getDefault().post(new UserBlockEvent(userBlock));
        } else {
          Log.e(TAG, "blockUser STATUS: " + response.code());
          EventBus.getDefault().post(new UserBlockEvent(null));
        }
      }

      @Override public void onFailure(Call<UserBlock> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserBlockEvent(null));
      }
    });
  }

  @Override public void unBlockUser(String token, String loginName) {
    Call<UserUnBlock> call = userService.unBlockUser(token, loginName);
    call.enqueue(new Callback<UserUnBlock>() {
      @Override public void onResponse(Call<UserUnBlock> call, Response<UserUnBlock> response) {
        if (response.isSuccessful()) {
          UserUnBlock userUnBlock = response.body();
          Log.v(TAG, "unBlockUser: " + userUnBlock);
          EventBus.getDefault().post(new UserUnBlockEvent(userUnBlock));
        } else {
          Log.e(TAG, "unBlockUser STATUS: " + response.code());
          EventBus.getDefault().post(new UserUnBlockEvent(null));
        }
      }

      @Override public void onFailure(Call<UserUnBlock> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserUnBlockEvent(null));
      }
    });
  }

  @Override public void followUser(String token, String loginName) {
    Call<UserFollow> call = userService.followUser(token, loginName);
    call.enqueue(new Callback<UserFollow>() {
      @Override public void onResponse(Call<UserFollow> call, Response<UserFollow> response) {
        if (response.isSuccessful()) {
          UserFollow userFollow = response.body();
          Log.v(TAG, "userFollow: " + userFollow);
          EventBus.getDefault().post(new UserFollowEvent(userFollow));
        } else {
          Log.e(TAG, "followUser STATUS: " + response.code());
          EventBus.getDefault().post(new UserFollowEvent(null));
        }
      }

      @Override public void onFailure(Call<UserFollow> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserFollowEvent(null));
      }
    });
  }

  @Override public void unFollowUser(String token, String loginName) {
    Call<UserUnFollow> call = userService.unFollowUser(token, loginName);
    call.enqueue(new Callback<UserUnFollow>() {
      @Override public void onResponse(Call<UserUnFollow> call, Response<UserUnFollow> response) {
        if (response.isSuccessful()) {
          UserUnFollow userUnFollow = response.body();
          Log.v(TAG, "userUnFollow: " + userUnFollow);
          EventBus.getDefault().post(new UserUnFollowEvent(userUnFollow));
        } else {
          Log.e(TAG, "followUser STATUS: " + response.code());
          EventBus.getDefault().post(new UserUnFollowEvent(null));
        }
      }

      @Override public void onFailure(Call<UserUnFollow> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserUnFollowEvent(null));
      }
    });
  }

  @Override public void favoriteTopic(String token, int id) {

  }

  @Override public void unFavoriteTopic(String token, int id) {

  }

  @Override public void followTopic(String token, int id) {

  }

  @Override public void unFollowTopic(String token, int id) {

  }

  @Override public void getUserCreateTopics(String loginName, Integer offset, Integer limit) {
    Call<List<Topic>> call = userService.getUserCreateTopics(loginName, offset, limit);

    call.enqueue(new Callback<List<Topic>>() {
      @Override public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
        if (response.isSuccessful()) {
          List<Topic> topicList = response.body();
          Log.v(TAG, "topicList:" + topicList);
          EventBus.getDefault().post(new UserTopicsEvent(topicList));
        } else {
          Log.e(TAG, "getUserCreateTopics STATUS: " + response.code());
          EventBus.getDefault().post(new UserTopicsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserTopicsEvent(null));
      }
    });
  }

  @Override public void getUserFavoriteTopics(String loginName, Integer offset, Integer limit) {
    Call<List<Topic>> call = userService.getUserFavoriteTopics(loginName, offset, limit);

    call.enqueue(new Callback<List<Topic>>() {
      @Override public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
        if (response.isSuccessful()) {
          List<Topic> topicList = response.body();
          Log.v(TAG, "topicList:" + topicList);
          EventBus.getDefault().post(new UserFavoriteTopicsEvent(topicList));
        } else {
          Log.e(TAG, "getUserFavoriteTopics STATUS: " + response.code());
          EventBus.getDefault().post(new UserFavoriteTopicsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserFavoriteTopicsEvent(null));
      }
    });
  }

  @Override public void getUserReplies(String loginName, Integer offset, Integer limit) {
    Call<List<Reply>> call = userService.getUserReplies(loginName, offset, limit);
    call.enqueue(new Callback<List<Reply>>() {
      @Override public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
        if (response.isSuccessful()) {
          List<Reply> replyList = response.body();
          Log.v(TAG, "replyList: " + replyList);
          EventBus.getDefault().postSticky(new RepliesEvent(replyList));
        } else {
          Log.e(TAG, "getUserReplies STATUS: " + response.code());
          EventBus.getDefault().postSticky(new RepliesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Reply>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new RepliesEvent(null));
      }
    });
  }
}
