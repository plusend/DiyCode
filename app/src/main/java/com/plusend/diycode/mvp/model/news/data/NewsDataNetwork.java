package com.plusend.diycode.mvp.model.news.data;

import android.util.Log;
import com.plusend.diycode.mvp.model.news.entity.News;
import com.plusend.diycode.mvp.model.news.event.NewsEvent;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsDataNetwork implements NewsData {
  private static final String TAG = "NetworkData";
  private NewsService service;
  private static NewsDataNetwork networkData = new NewsDataNetwork();

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
}
