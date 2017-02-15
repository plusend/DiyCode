package com.plusend.diycode.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity
    implements BGASwipeBackHelper.Delegate {
  private static final String TAG = "BaseActivity";
  protected BGASwipeBackHelper mSwipeBackHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    initSwipeBackFinish();
    super.onCreate(savedInstanceState);
    initActionBar(getToolbar());
  }

  protected abstract Toolbar getToolbar();

  protected abstract List<Presenter> getPresenter();

  private void initActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override protected void onStart() {
    super.onStart();
    if (getPresenter() != null && getPresenter().size() != 0) {
      for (Presenter presenter : getPresenter()) {
        presenter.start();
      }
    } else {
      Log.d(TAG, "onStart getPresenter() == null");
    }
  }

  @Override protected void onStop() {
    if (getPresenter() != null && getPresenter().size() != 0) {
      for (Presenter presenter : getPresenter()) {
        presenter.stop();
      }
    } else {
      Log.d(TAG, "onStop getPresenter() == null");
    }
    super.onStop();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
   */
  private void initSwipeBackFinish() {
    mSwipeBackHelper = new BGASwipeBackHelper(this, this);

    // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
    // 下面几项可以不配置，这里只是为了讲述接口用法。

    // 设置滑动返回是否可用。默认值为 true
    mSwipeBackHelper.setSwipeBackEnable(true);
    // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
    mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
    // 设置是否是微信滑动返回样式。默认值为 true
    mSwipeBackHelper.setIsWeChatStyle(true);
    // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
    mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
    // 设置是否显示滑动返回的阴影效果。默认值为 true
    mSwipeBackHelper.setIsNeedShowShadow(true);
    // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
    mSwipeBackHelper.setIsShadowAlphaGradient(true);
  }

  /**
   * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
   */
  @Override public boolean isSupportSwipeBack() {
    return true;
  }

  /**
   * 正在滑动返回
   *
   * @param slideOffset 从 0 到 1
   */
  @Override public void onSwipeBackLayoutSlide(float slideOffset) {
  }

  /**
   * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
   */
  @Override public void onSwipeBackLayoutCancel() {
  }

  /**
   * 滑动返回执行完毕，销毁当前 Activity
   */
  @Override public void onSwipeBackLayoutExecuted() {
    mSwipeBackHelper.swipeBackward();
  }
}
