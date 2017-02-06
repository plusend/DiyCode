package com.plusend.diycode.mvp.model.site.presenter;

import android.util.Log;
import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.site.data.SiteDataNetwork;
import com.plusend.diycode.mvp.model.site.event.SiteEvent;
import com.plusend.diycode.mvp.model.site.view.SiteView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SitePresenter extends Presenter {
  private static final String TAG = "SitePresenter";
  private BaseData data;
  private SiteView siteView;

  public SitePresenter(SiteView siteView) {
    this.siteView = siteView;
    data = SiteDataNetwork.getInstance();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showSite(SiteEvent siteEvent) {
    this.siteView.showSite(siteEvent.getSiteList());
  }

  public void getSite(){
    ((SiteDataNetwork) data).getSite();
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
