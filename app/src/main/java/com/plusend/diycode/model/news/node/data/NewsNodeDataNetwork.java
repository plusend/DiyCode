package com.plusend.diycode.model.news.node.data;

import android.util.Log;
import com.plusend.diycode.model.news.node.entity.NewsNode;
import com.plusend.diycode.model.news.node.event.NewsNodesEvent;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsNodeDataNetwork implements NewsNodeData {
  private static final String TAG = "NetworkData";
  private NewsNodeService service;
  private static NewsNodeDataNetwork networkData = new NewsNodeDataNetwork();

  private NewsNodeDataNetwork() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    service = retrofit.create(NewsNodeService.class);
  }

  public static NewsNodeDataNetwork getInstance() {
    return networkData;
  }

  @Override public void readNewsNodes() {
    Call<List<NewsNode>> call = service.readNewsNodes();
    call.enqueue(new Callback<List<NewsNode>>() {
      @Override
      public void onResponse(Call<List<NewsNode>> call, Response<List<NewsNode>> response) {
        if (response.isSuccessful()) {
          List<NewsNode> newsNodeList = response.body();
          Log.v(TAG, "newsNodeList:" + newsNodeList);
          EventBus.getDefault().postSticky(new NewsNodesEvent(newsNodeList));
        } else {
          Log.e(TAG, "readNewsNodes STATUS: " + response.code());
          EventBus.getDefault().postSticky(new NewsNodesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<NewsNode>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new NewsNodesEvent(null));
      }
    });
  }
}
