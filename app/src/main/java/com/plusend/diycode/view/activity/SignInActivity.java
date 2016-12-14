package com.plusend.diycode.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.entity.Token;
import com.plusend.diycode.mvp.presenter.SignInPresenter;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.mvp.view.SignInView;

public class SignInActivity extends AppCompatActivity implements SignInView {

  public static final int REQUEST_CODE = 1;
  public static final int RESULT_OK = 200;
  public static final int RESULT_ERROR = 401;

  @BindView(R.id.close) ImageView close;
  @BindView(R.id.name) EditText name;
  @BindView(R.id.password) EditText password;
  @BindView(R.id.sign_in) Button signIn;
  @BindView(R.id.sign_github) ImageView signGithub;
  @BindView(R.id.sign_weibo) ImageView signWeibo;
  @BindView(R.id.sign_up) TextView signUp;
  @BindView(R.id.forget_password) TextView forgetPassword;

  private SignInPresenter signInPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    ButterKnife.bind(this);

    signInPresenter = new SignInPresenter(this);

    signIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Constant.VALUE_USERNAME = name.getText().toString();
        Constant.VALUE_PASSWORD = password.getText().toString();
        signInPresenter.getToken();
      }
    });

    close.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });
  }

  @Override public void getToken(Token token) {
    if (token != null) {

      PrefUtil.saveToken(this, token);

      Constant.VALUE_TOKEN = token.getAccessToken();
      Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
      setResult(RESULT_OK);
      finish();
    } else {
      Toast.makeText(this, "Email / 用户名或密码错误，登录失败", Toast.LENGTH_SHORT).show();
    }
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onStart() {
    super.onStart();
    signInPresenter.start();
  }

  @Override protected void onStop() {
    signInPresenter.stop();
    super.onStop();
  }
}
