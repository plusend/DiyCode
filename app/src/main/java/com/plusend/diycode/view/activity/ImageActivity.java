package com.plusend.diycode.view.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;
import java.util.List;
import me.relex.photodraweeview.PhotoDraweeView;

public class ImageActivity extends BaseActivity {
  private static final String TAG = "ImageActivity";
  public static final String URL = "url";

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.image) PhotoDraweeView mPhotoDraweeView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_image);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    String url = getIntent().getStringExtra(URL);
    Log.d(TAG, "url: " + url);

    DraweeController controller = Fresco.newDraweeControllerBuilder()
        .setUri(Uri.parse(url))
        .setAutoPlayAnimations(true)
        .setControllerListener(new BaseControllerListener<ImageInfo>() {
          @Override
          public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            if (imageInfo == null || mPhotoDraweeView == null) {
              return;
            }
            mPhotoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
          }
        })
        .build();
    mPhotoDraweeView.setController(controller);
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<Presenter> getPresenter() {
    return null;
  }
}
