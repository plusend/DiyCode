package com.plusend.diycode.mvp.model.site.data;

import android.util.Log;
import com.plusend.diycode.mvp.model.site.entity.Site;
import com.plusend.diycode.mvp.model.site.event.SiteEvent;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SiteDataNetwork implements SiteData {
  private static final String TAG = "NetworkData";
  private SiteService service;
  private static SiteDataNetwork networkData = new SiteDataNetwork();

  private SiteDataNetwork() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.diycode.cc/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    service = retrofit.create(SiteService.class);
  }

  public static SiteDataNetwork getInstance() {
    return networkData;
  }

  @Override public void getSite() {
    Call<List<Site>> call = service.getSite();

    call.enqueue(new Callback<List<Site>>() {
      @Override public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {
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
}
