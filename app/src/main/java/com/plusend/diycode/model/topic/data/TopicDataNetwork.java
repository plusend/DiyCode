package com.plusend.diycode.model.topic.data;

import android.util.Log;
import com.plusend.diycode.model.topic.entity.FavoriteTopic;
import com.plusend.diycode.model.topic.entity.FollowTopic;
import com.plusend.diycode.model.topic.entity.Like;
import com.plusend.diycode.model.topic.entity.Topic;
import com.plusend.diycode.model.topic.entity.TopicDetail;
import com.plusend.diycode.model.topic.entity.TopicReply;
import com.plusend.diycode.model.topic.entity.UnFavoriteTopic;
import com.plusend.diycode.model.topic.entity.UnFollowTopic;
import com.plusend.diycode.model.topic.event.CreateTopicEvent;
import com.plusend.diycode.model.topic.event.CreateTopicReplyEvent;
import com.plusend.diycode.model.topic.event.FavoriteEvent;
import com.plusend.diycode.model.topic.event.FollowEvent;
import com.plusend.diycode.model.topic.event.LikeEvent;
import com.plusend.diycode.model.topic.event.TopTopicsEvent;
import com.plusend.diycode.model.topic.event.TopicDetailEvent;
import com.plusend.diycode.model.topic.event.TopicRepliesEvent;
import com.plusend.diycode.model.topic.event.TopicsEvent;
import com.plusend.diycode.model.topic.event.UnFavoriteEvent;
import com.plusend.diycode.model.topic.event.UnFollowEvent;
import com.plusend.diycode.model.topic.event.UnLikeEvent;
import com.plusend.diycode.util.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopicDataNetwork implements TopicData {
    private static final String TAG = "NetworkData";
    private static TopicDataNetwork networkData = new TopicDataNetwork();
    private TopicService service;

    private TopicDataNetwork() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.addInterceptor(new Interceptor() {
            @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (Constant.VALUE_TOKEN != null) {
                    builder.addHeader(Constant.KEY_TOKEN,
                        Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN);
                }
                return chain.proceed(builder.build());
            }
        });
        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
        service = retrofit.create(TopicService.class);
    }

    public static TopicDataNetwork getInstance() {
        return networkData;
    }

    @Override public void getTopics(String type, Integer nodeId, Integer offset, Integer limit) {
        Call<List<Topic>> call = service.getTopics(type, nodeId, offset, limit);
        call.enqueue(new Callback<List<Topic>>() {
            @Override public void onResponse(Call<List<Topic>> call,
                retrofit2.Response<List<Topic>> response) {
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

    @Override public void getTopTopics() {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.diycode.cc/").get();
                    int size = doc.getElementsByClass("fa fa-thumb-tack").size();
                    Log.d(TAG, "top size: " + size);
                    Elements elements = doc.getElementsByClass("panel-body");
                    Elements topics = elements.get(0).children();
                    Log.d(TAG, "topics size: " + topics.size());
                    List<Topic> topicList = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        Element topic = topics.get(i);
                        Topic temp = new Topic();
                        String href = topic.getElementsByClass("title media-heading")
                            .get(0)
                            .getElementsByTag("a")
                            .attr("href");
                        temp.setId(Integer.valueOf(href.substring(href.lastIndexOf("/") + 1)));
                        temp.setTitle(
                            topic.getElementsByClass("title media-heading").get(0).text());
                        temp.setNodeName(topic.getElementsByClass("node").get(0).text());
                        String time = topic.getElementsByClass("timeago").get(0).attr("title");
                        StringBuilder sb = new StringBuilder(time);
                        sb.insert(19, ".000");
                        time = sb.toString();
                        temp.setRepliedAt(time);
                        Topic.User user = new Topic.User();
                        user.setAvatarUrl(topic.getElementsByTag("img").get(0).attr("src"));
                        user.setLogin(topic.getElementsByClass("hacknews_clear").get(1).text());
                        temp.setUser(user);
                        temp.setPin(true);
                        topicList.add(temp);
                    }
                    EventBus.getDefault().post(new TopTopicsEvent(topicList));
                } catch (IOException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new TopTopicsEvent(null));
                }
            }
        }).start();
    }

    @Override public void getTopic(int id) {
        Call<TopicDetail> call = service.getTopic(id);
        call.enqueue(new Callback<TopicDetail>() {
            @Override public void onResponse(Call<TopicDetail> call,
                retrofit2.Response<TopicDetail> response) {
                if (response.isSuccessful()) {
                    TopicDetail topicDetail = response.body();
                    Log.v(TAG, "getTopic topicDetail:" + topicDetail);
                    EventBus.getDefault().post(new TopicDetailEvent(topicDetail));
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

    @Override public void newTopic(final String title, String body, int nodeId) {
        Call<TopicDetail> call = service.newTopic(title, body, nodeId);
        call.enqueue(new Callback<TopicDetail>() {
            @Override
            public void onResponse(Call<TopicDetail> call, Response<TopicDetail> response) {
                if (response.isSuccessful()) {
                    TopicDetail topicDetail = response.body();
                    Log.v(TAG, "topicDetail: " + topicDetail);
                    EventBus.getDefault().postSticky(new CreateTopicEvent(topicDetail));
                    EventBus.getDefault().post(new CreateTopicEvent(topicDetail));
                } else {
                    Log.e(TAG, "newTopic STATUS: " + response.code());
                    EventBus.getDefault().post(new CreateTopicEvent(null));
                }
            }

            @Override public void onFailure(Call<TopicDetail> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new CreateTopicEvent(null));
            }
        });
    }

    @Override public void favorite(int id) {
        Call<FavoriteTopic> call = service.favoriteTopic(id);
        call.enqueue(new Callback<FavoriteTopic>() {
            @Override
            public void onResponse(Call<FavoriteTopic> call, Response<FavoriteTopic> response) {
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
        Call<UnFavoriteTopic> call = service.unFavoriteTopic(id);
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
        Call<FollowTopic> call = service.followTopic(id);
        call.enqueue(new Callback<FollowTopic>() {
            @Override
            public void onResponse(Call<FollowTopic> call, Response<FollowTopic> response) {
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
        Call<UnFollowTopic> call = service.unFollowTopic(id);
        call.enqueue(new Callback<UnFollowTopic>() {
            @Override
            public void onResponse(Call<UnFollowTopic> call, Response<UnFollowTopic> response) {
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

    @Override public void like(String obj_type, Integer obj_id) {
        Call<Like> call = service.like(obj_type, obj_id);
        call.enqueue(new Callback<Like>() {
            @Override public void onResponse(Call<Like> call, Response<Like> response) {
                if (response.isSuccessful()) {
                    Like like = response.body();
                    Log.v(TAG, "like: " + like);
                    EventBus.getDefault().post(new LikeEvent(like));
                } else {
                    Log.e(TAG, "like STATUS: " + response.code());
                    EventBus.getDefault().post(new LikeEvent(null));
                }
            }

            @Override public void onFailure(Call<Like> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new LikeEvent(null));
            }
        });
    }

    @Override public void unLike(String obj_type, Integer obj_id) {
        Call<Like> call = service.unLike(obj_type, obj_id);
        call.enqueue(new Callback<Like>() {
            @Override public void onResponse(Call<Like> call, Response<Like> response) {
                if (response.isSuccessful()) {
                    Like like = response.body();
                    Log.v(TAG, "unLike: " + like);
                    EventBus.getDefault().post(new UnLikeEvent(like));
                } else {
                    Log.e(TAG, "unLike STATUS: " + response.code());
                    EventBus.getDefault().post(new UnLikeEvent(null));
                }
            }

            @Override public void onFailure(Call<Like> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new UnLikeEvent(null));
            }
        });
    }

    @Override public void createReply(int id, String body) {
        Call<TopicReply> call = service.createReply(id, body);
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
}
