package com.plusend.diycode.view.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
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

    GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
    GenericDraweeHierarchy hierarchy =
        builder.setFadeDuration(300).setProgressBarImage(new ProgressBarDrawable()).build();
    mPhotoDraweeView.setHierarchy(hierarchy);

    // 需要使用 ControllerBuilder 方式请求图片
    PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
    controller.setUri(Uri.parse(url));
    controller.setOldController(mPhotoDraweeView.getController());
    controller.setAutoPlayAnimations(true);

    // 需要设置 ControllerListener，获取图片大小后，传递给 PhotoDraweeView 更新图片长宽
    controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
      @Override public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
        super.onFinalImageSet(id, imageInfo, animatable);
        if (imageInfo == null || mPhotoDraweeView == null) {
          return;
        }
        mPhotoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
      }
    });
    mPhotoDraweeView.setController(controller.build());
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<Presenter> getPresenter() {
    return null;
  }
}
