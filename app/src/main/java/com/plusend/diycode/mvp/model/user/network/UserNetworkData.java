package com.plusend.diycode.mvp.model.user.network;

import android.util.Log;
import com.plusend.diycode.event.TopicsEvent;
import com.plusend.diycode.event.UserBlockedEvent;
import com.plusend.diycode.event.UserDetailInfoEvent;
import com.plusend.diycode.event.UserFollowersEvent;
import com.plusend.diycode.event.UserFollowingEvent;
import com.plusend.diycode.mvp.model.entity.Topic;
import com.plusend.diycode.mvp.model.user.UserData;
import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;
import com.plusend.diycode.mvp.model.user.entity.UserInfo;
import com.plusend.diycode.util.Constant;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserNetworkData implements UserData {
  private static final String TAG = "UserNetworkData";

  private UserInfoService userInfoService;
  private static UserNetworkData userNetworkData = new UserNetworkData();

  private UserNetworkData() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    userInfoService = retrofit.create(UserInfoService.class);
  }

  public static UserNetworkData getInstance() {
    return userNetworkData;
  }

  @Override public void getUser(String token, String loginName) {
    Call<UserDetailInfo> call =
        userInfoService.getUser(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName);
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
        EventBus.getDefault().post(new UserDetailInfoEvent(null));
      }
    });
  }

  @Override
  public void getUserBlocked(String token, String loginName, Integer offset, Integer limit) {
    Call<List<UserInfo>> call =
        userInfoService.getUserBlocked(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN,
            loginName, offset, limit);
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
        userInfoService.getUserFollowing(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN,
            loginName, offset, limit);
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
        userInfoService.getUserFollowers(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN,
            loginName, offset, limit);
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

  @Override public void getUserFavoriteTopics(String loginName, Integer offset, Integer limit) {
    Call<List<Topic>> call = userInfoService.getUserFavoriteTopics(loginName, offset, limit);

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

  @Override public void blockUser(String token, String loginName) {

  }

  @Override public void unBlockUser(String token, String loginName) {

  }

  @Override public void followUser(String token, String loginName) {

  }

  @Override public void unFollowUser(String token, String loginName) {

  }

  @Override public void favoriteTopic(String token, int id) {

  }

  @Override public void unFavoriteTopic(String token, int id) {

  }

  @Override public void followTopic(String token, int id) {

  }

  @Override public void unFollowTopic(String token, int id) {

  }
}
