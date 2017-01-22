package com.plusend.diycode.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import com.plusend.diycode.mvp.model.base.Presenter;

public abstract class BaseActivity extends AppCompatActivity {
  private static final String TAG = "BaseActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initActionBar(getToolbar());
  }

  protected abstract Toolbar getToolbar();

  protected abstract Presenter getPresenter();

  private void initActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override protected void onStart() {
    super.onStart();
    if (getPresenter() != null) {
      getPresenter().start();
    } else {
      Log.d(TAG, "onStart getPresenter() == null");
    }
  }

  @Override protected void onStop() {
    if (getPresenter() != null) {
      getPresenter().stop();
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
}
