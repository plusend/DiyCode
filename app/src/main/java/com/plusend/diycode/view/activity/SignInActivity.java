package com.plusend.diycode.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.model.entity.User;
import com.plusend.diycode.mvp.presenter.SignInPresenter;
import com.plusend.diycode.mvp.presenter.UserPresenter;
import com.plusend.diycode.mvp.view.SignInView;
import com.plusend.diycode.mvp.view.UserView;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.util.ToastUtil;

public class SignInActivity extends AppCompatActivity implements SignInView, UserView {

  public static final int REQUEST_CODE = 1;
  public static final int RESULT_OK = 200;
  public static final int RESULT_ERROR = 401;

  @BindView(R.id.name) EditText name;
  @BindView(R.id.password) EditText password;
  @BindView(R.id.sign_in) Button signIn;
  @BindView(R.id.sign_github) ImageView signGithub;
  @BindView(R.id.sign_weibo) ImageView signWeibo;
  @BindView(R.id.sign_up) TextView signUp;
  @BindView(R.id.forget_password) TextView forgetPassword;
  @BindView(R.id.toolbar) Toolbar toolbar;

  private SignInPresenter signInPresenter;
  private UserPresenter userPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    ButterKnife.bind(this);
    initActionBar(toolbar);

    signInPresenter = new SignInPresenter(this);
    userPresenter = new UserPresenter(this);

    signIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String username = name.getText().toString();
        String passwordString = password.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passwordString)) {
          ToastUtil.showText(SignInActivity.this, "Email / 用户名或密码为空");
          return;
        }
        signInPresenter.getToken(username, passwordString);
      }
    });
  }

  private void initActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override public void getToken(Token token) {
    if (token != null) {
      PrefUtil.saveToken(this, token);
      userPresenter.getMe();
    } else {
      ToastUtil.showText(this, "Email / 用户名或密码错误，登录失败");
    }
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onStart() {
    super.onStart();
    signInPresenter.start();
    userPresenter.start();
  }

  @Override protected void onStop() {
    signInPresenter.stop();
    userPresenter.stop();
    super.onStop();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void getMe(User user) {
    if (user == null) {
      ToastUtil.showText(this, "网络出问题了，登录失败");
      setResult(RESULT_ERROR);
    } else {
      PrefUtil.saveMe(this, user);
      ToastUtil.showText(this, "登录成功");
      setResult(RESULT_OK);
    }
    finish();
  }

  @Override public void getUser(User user) {

  }
}
