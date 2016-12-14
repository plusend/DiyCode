package com.plusend.diycode.mvp.presenter;

import com.plusend.diycode.event.NodesEvent;
import com.plusend.diycode.mvp.model.Data;
import com.plusend.diycode.mvp.model.network.NetworkData;
import com.plusend.diycode.mvp.view.NodesView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by plusend on 2016/12/5.
 */

public class NodesPresenter extends Presenter {
  private static final String TAG = "NodesPresenter";
  private NodesView nodesView;
  private Data data;

  public NodesPresenter(NodesView nodesView) {
    this.nodesView = nodesView;
    data = NetworkData.getInstance();
  }

  public void readNodes() {
    data.readNodes();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showNodes(NodesEvent nodesEvent) {
    nodesView.showNodes(nodesEvent.getNodeList());
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
