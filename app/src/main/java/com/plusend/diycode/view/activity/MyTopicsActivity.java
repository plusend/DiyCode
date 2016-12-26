package com.plusend.diycode.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.util.ToastUtil;
import com.plusend.diycode.view.fragment.TopicFragment;

public class MyTopicsActivity extends AppCompatActivity {

  public static final String TITLE = "title";
  public static final String TYPE = "type";
  private String title;
  private int type;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_topics);
    ButterKnife.bind(this);

    initActionBar(toolbar);
    Intent intent = getIntent();
    if (intent != null) {
      title = intent.getStringExtra(TITLE);
      type = intent.getIntExtra(TYPE, TopicFragment.TYPE_ALL);
    }

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(title);
    }

    String loginName = PrefUtil.getMe(this).getLogin();
    if (TextUtils.isEmpty(loginName)) {
      startActivityForResult(new Intent(MyTopicsActivity.this, SignInActivity.class),
          SignInActivity.REQUEST_CODE);
      ToastUtil.showText(MyTopicsActivity.this, "请先登录");
      return;
    }

    addTopicFragment();
  }

  private void initActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
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

  private void addTopicFragment() {
    TopicFragment topicFragment = TopicFragment.newInstance(PrefUtil.getMe(this).getLogin(), type);
    //bundle.putString(Constant.User.LOGIN, PrefUtil.getMe(this).getLogin());
    getSupportFragmentManager().beginTransaction().add(R.id.container, topicFragment).commit();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode == SignInActivity.RESULT_OK) {
          addTopicFragment();
        } else {
          ToastUtil.showText(MyTopicsActivity.this, "放弃登录");
          finish();
        }
        break;
    }
  }
}
