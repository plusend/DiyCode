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
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.presenter.CreateTopicReplyPresenter;
import com.plusend.diycode.model.topic.view.CreateTopicReplyView;
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
  }

  private void send() {
    if (TextUtils.isEmpty(body.getText())) {
      ToastUtil.showText(this, "评论内容不能为空");
      return;
    }
    createTopicReplyPresenter.createTopicReply(id, body.getText().toString());
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<BasePresenter> getPresenter() {
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
