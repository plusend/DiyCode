package com.plusend.diycode.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.plusend.diycode.R;
import java.util.HashSet;
import java.util.Set;

public final class GlideImageGetter implements Html.ImageGetter, Drawable.Callback {

  private final Context mContext;

  private final TextView mTextView;

  private final Set<ImageGetterViewTarget> mTargets;

  public static GlideImageGetter get(View view) {
    return (GlideImageGetter)view.getTag(R.id.drawable_callback_tag);
  }

  public void clear() {
    GlideImageGetter prev = get(mTextView);
    if (prev == null) return;

    for (ImageGetterViewTarget target : prev.mTargets) {
      Glide.clear(target);
    }
  }

  public GlideImageGetter(Context context, TextView textView) {
    this.mContext = context;
    this.mTextView = textView;

    clear();
    mTargets = new HashSet<>();
    mTextView.setTag(R.id.drawable_callback_tag, this);
  }

  @Override
  public Drawable getDrawable(String url) {
    final UrlDrawable urlDrawable = new UrlDrawable();


    System.out.println("Downloading from: " + url);
    Glide.with(mContext)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(new ImageGetterViewTarget(mTextView, urlDrawable));


    return urlDrawable;

  }

  @Override
  public void invalidateDrawable(Drawable who) {
    mTextView.invalidate();
  }

  @Override
  public void scheduleDrawable(Drawable who, Runnable what, long when) {

  }

  @Override
  public void unscheduleDrawable(Drawable who, Runnable what) {

  }

  private class ImageGetterViewTarget extends ViewTarget<TextView, GlideDrawable> {

    private final UrlDrawable mDrawable;

    private ImageGetterViewTarget(TextView view, UrlDrawable drawable) {
      super(view);
      mTargets.add(this);
      this.mDrawable = drawable;
    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
      Rect rect;
      if (resource.getIntrinsicWidth() > 100) {
        float width;
        float height;
        System.out.println("Image width is " + resource.getIntrinsicWidth());
        System.out.println("View width is " + view.getWidth());
        if (resource.getIntrinsicWidth() >= getView().getWidth()) {
          float downScale = (float) resource.getIntrinsicWidth() / getView().getWidth();
          width = (float) resource.getIntrinsicWidth() / (float) downScale;
          height = (float) resource.getIntrinsicHeight() / (float) downScale;
        } else {
          float multiplier = (float) getView().getWidth() / resource.getIntrinsicWidth();
          width = (float) resource.getIntrinsicWidth() * (float) multiplier;
          height = (float) resource.getIntrinsicHeight() * (float) multiplier;
        }
        System.out.println("New Image width is " + width);


        rect = new Rect(0, 0, Math.round(width), Math.round(height));
      } else {
        rect = new Rect(0, 0, resource.getIntrinsicWidth() * 2, resource.getIntrinsicHeight() * 2);
      }
      resource.setBounds(rect);

      mDrawable.setBounds(rect);
      mDrawable.setDrawable(resource);


      if (resource.isAnimated()) {
        mDrawable.setCallback(get(getView()));
        resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
        resource.start();
      }

      getView().setText(getView().getText());
      getView().invalidate();
    }

    private Request request;
    @Override
    public Request getRequest() {
      return request;
    }

    @Override
    public void setRequest(Request request) {
      this.request = request;
    }
  }
}