package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.topic.presenter.CreateTopicReplyPresenter;
import com.plusend.diycode.mvp.model.topic.view.CreateTopicReplyView;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.ToastUtil;
import java.util.List;

public class CreateTopicReplyActivity extends BaseActivity implements CreateTopicReplyView {

  @BindView(R.id.title) TextView title;
  @BindView(R.id.body) EditText body;
  @BindView(R.id.toolbar) Toolbar toolbar;
  public static final String TO = "toSb";
  public static final String TOPIC_ID = "topicId";
  public static final String TOPIC_TITLE = "topicTitle";

  private CreateTopicReplyPresenter createTopicReplyPresenter;
  private int id;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_create_topic_reply);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    id = intent.getIntExtra(TOPIC_ID, 0);
    String titleString = intent.getStringExtra(TOPIC_TITLE);
    title.setText(titleString);
    String contentPrefix = intent.getStringExtra(TO);
    if (!TextUtils.isEmpty(contentPrefix)) {
      body.setText(contentPrefix);
      body.setSelection(contentPrefix.length());
    }
    body.requestFocus();

    createTopicReplyPresenter = new CreateTopicReplyPresenter(this);

    if (TextUtils.isEmpty(Constant.VALUE_TOKEN)) {
      startActivityForResult(new Intent(this, SignInActivity.class), SignInActivity.REQUEST_CODE);
      ToastUtil.showText(this, "请先登录");
    }
  }

  private void send() {
    createTopicReplyPresenter.createTopicReply(id, body.getText().toString());
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<Presenter> getPresenter() {
    return super.addPresenter(createTopicReplyPresenter);
  }

  @Override public void getResult(boolean isSuccessful) {
    if (isSuccessful) {
      finish();
    } else {
      ToastUtil.showText(this, "发布失败");
    }
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode != SignInActivity.RESULT_OK) {
          ToastUtil.showText(this, "放弃登录");
          finish();
        }
        break;
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      case R.id.action_send:
        send();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_create_topic_reply, menu);
    return super.onCreateOptionsMenu(menu);
  }
}
