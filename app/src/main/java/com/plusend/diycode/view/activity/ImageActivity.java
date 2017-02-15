package com.plusend.diycode.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;
import java.util.List;
import uk.co.senab.photoview.PhotoView;

public class ImageActivity extends BaseActivity {
  private static final String TAG = "ImageActivity";
  public static final String URL = "url";

  @BindView(R.id.image) PhotoView image;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_image);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    String url = getIntent().getStringExtra(URL);
    Log.d(TAG, "url: " + url);

    Glide.with(this).load(url).asBitmap().into(image);
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<Presenter> getPresenter() {
    return null;
  }
}
