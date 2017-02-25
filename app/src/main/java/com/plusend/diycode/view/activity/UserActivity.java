package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.user.entity.UserDetailInfo;
import com.plusend.diycode.model.user.presenter.UserPresenter;
import com.plusend.diycode.model.user.view.UserView;
import com.plusend.diycode.view.fragment.TopicFragment;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserActivity extends BaseActivity implements UserView {
  private static final String TAG = "UserActivity";
  public static final String LOGIN_NAME = "loginName";
  @BindView(R.id.avatar) ImageView avatar;
  @BindView(R.id.name) TextView name;
  @BindView(R.id.topic_num) TextView topicNum;
  @BindView(R.id.favorite_num) TextView favoriteNum;
  @BindView(R.id.follow_num) TextView followNum;
  @BindView(R.id.back) ImageView back;
  @BindView(R.id.container) FrameLayout container;
  private UserPresenter userPresenter;
  private String loginName;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_user);
    StatusBarUtil.setColorForSwipeBack(this, 0x284fbb);

    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });

    Intent intent = getIntent();
    loginName = intent.getStringExtra(LOGIN_NAME);

    TopicFragment topicFragment = TopicFragment.newInstance(loginName, TopicFragment.TYPE_CREATE);
    getSupportFragmentManager().beginTransaction().add(R.id.container, topicFragment).commit();

    userPresenter = new UserPresenter(this);
    userPresenter.getUser(loginName);
  }

  @Override protected Toolbar getToolbar() {
    return null;
  }

  @Override protected List<BasePresenter> getPresenter() {
    return super.addPresenter(userPresenter);
  }

  @Override public void getMe(UserDetailInfo userDetailInfo) {
  }

  @Override public void getUser(UserDetailInfo userDetailInfo) {
    Log.d(TAG, "getUser: " + userDetailInfo);
    if (userDetailInfo != null) {
      name.setText(userDetailInfo.getLogin());
      topicNum.setText(String.valueOf(userDetailInfo.getTopicsCount()));
      followNum.setText(String.valueOf(userDetailInfo.getFollowingCount()));
      favoriteNum.setText(String.valueOf(userDetailInfo.getFavoritesCount()));
      Glide.with(this)
          .load(userDetailInfo.getAvatarUrl())
          .bitmapTransform(new CropCircleTransformation(this))
          .crossFade()
          .into(avatar);
    }
  }

  @Override public Context getContext() {
    return this;
  }
}
