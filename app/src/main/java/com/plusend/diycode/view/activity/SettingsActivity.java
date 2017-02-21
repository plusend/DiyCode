package com.plusend.diycode.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.BasePresenter;
import com.plusend.diycode.view.fragment.SettingsFragment;
import java.util.List;

public class SettingsActivity extends BaseActivity {
  private static final String TAG = "SettingsActivity";
  @BindView(R.id.container) FrameLayout container;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_settings);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
        .replace(R.id.container, new SettingsFragment())
        .commit();
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<BasePresenter> getPresenter() {
    return null;
  }
}
