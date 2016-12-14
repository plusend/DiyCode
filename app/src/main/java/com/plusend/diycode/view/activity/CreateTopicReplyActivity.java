package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.presenter.CreateTopicReplyPresenter;
import com.plusend.diycode.mvp.view.CreateTopicReplyView;
import com.plusend.diycode.util.Constant;

public class CreateTopicReplyActivity extends AppCompatActivity implements CreateTopicReplyView {

  @BindView(R.id.title) TextView title;
  @BindView(R.id.body) EditText body;
  @BindView(R.id.fab) FloatingActionButton fab;

  private CreateTopicReplyPresenter createTopicReplyPresenter;
  private int id;
  private String titleString;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_topic_reply);
    ButterKnife.bind(this);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    Intent intent = getIntent();
    id = intent.getIntExtra(Constant.TOPIC_ID, 0);
    titleString = intent.getStringExtra(Constant.TOPIC_TITLE);
    title.setText(titleString);

    createTopicReplyPresenter = new CreateTopicReplyPresenter(this);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        createTopicReplyPresenter.createTopicReply(id, body.getText().toString());
      }
    });
  }

  @Override public void getResult(boolean isSuccessful) {
    if (isSuccessful) {
      finish();
    } else {
      Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
    }
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onStart() {
    super.onStart();
    createTopicReplyPresenter.start();
  }

  @Override protected void onStop() {
    createTopicReplyPresenter.stop();
    super.onStop();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case android.R.id.home:
        finish();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
