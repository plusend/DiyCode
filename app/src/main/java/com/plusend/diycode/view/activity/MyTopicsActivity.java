package com.plusend.diycode.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.util.PrefUtil;
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
    TopicFragment topicFragment = TopicFragment.newInstance(PrefUtil.getMe(this).getLogin(), type);
    //bundle.putString(Constant.User.LOGIN, PrefUtil.getMe(this).getLogin());
    getSupportFragmentManager().beginTransaction().add(R.id.container, topicFragment).commit();
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
}
