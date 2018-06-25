package com.plusend.diycode.model.news.data;

import android.util.Log;
import com.plusend.diycode.model.news.entity.News;
import com.plusend.diycode.model.news.event.CreateNewsEvent;
import com.plusend.diycode.model.news.event.NewsEvent;
import com.plusend.diycode.util.Constant;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDataNetwork implements NewsData {
    private static final String TAG = "NetworkData";
    private static NewsDataNetwork networkData = new NewsDataNetwork();
    private NewsService service;

    private NewsDataNetwork() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        service = retrofit.create(NewsService.class);
    }

    public static NewsDataNetwork getInstance() {
        return networkData;
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

    @Override public void createNews(String title, String address, Integer node_id) {
        Call<News> call =
            service.createNews(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, title, address,
                node_id);
        call.enqueue(new Callback<News>() {
            @Override public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    Log.v(TAG, "news: " + news);
                    EventBus.getDefault().post(new CreateNewsEvent(news));
                } else {
                    Log.e(TAG, "createNews STATUS: " + response.code());
                    EventBus.getDefault().post(new CreateNewsEvent(null));
                }
            }

            @Override public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                EventBus.getDefault().post(new CreateNewsEvent(null));
            }
        });
    }

    public void getLinkTitle(String link) {

    }
}
