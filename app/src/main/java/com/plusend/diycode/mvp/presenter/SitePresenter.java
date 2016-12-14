package com.plusend.diycode.mvp.presenter;

import android.util.Log;
import com.plusend.diycode.event.SiteEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.SiteView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/11/27.
 */

public class SitePresenter extends Presenter {
  private static final String TAG = "SitePresenter";
  private Data data;
  private SiteView siteView;

  public SitePresenter(SiteView siteView) {
    this.siteView = siteView;
    data = NetworkData.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showSite(SiteEvent siteEvent) {
    this.siteView.showSite(siteEvent.getSiteList());
  }

  @Override public void start() {
    Log.d(TAG, "register");
    EventBus.getDefault().register(this);
    this.data.getSite();
  }

  @Override public void stop() {
    Log.d(TAG, "unregister");
    EventBus.getDefault().unregister(this);
  }
}
