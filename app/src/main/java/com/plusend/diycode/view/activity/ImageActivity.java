package com.plusend.diycode.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import java.util.List;

public class ImageActivity extends BaseActivity {
    public static final String URL = "url";
    private static final String TAG = "ImageActivity";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.image) PhotoView image;

    @Override protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(URL);
        Log.d(TAG, "url: " + url);

        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this).load(url).apply(options).listener(new RequestListener<Drawable>() {
            @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
                Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(image);
    }

    @Override protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override protected List<BasePresenter> getPresenter() {
        return null;
    }
}
