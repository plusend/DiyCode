package com.plusend.diycode.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.util.ToastUtil;
import com.plusend.diycode.view.fragment.TopicFragment;
import java.util.List;

public class MyTopicsActivity extends BaseActivity {

    public static final String TITLE = "title";
    public static final String TYPE = "type";
    @BindView(R.id.toolbar) Toolbar toolbar;
    private String title;
    private int type;

    @Override protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_topics);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

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

    @Override protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override protected List<BasePresenter> getPresenter() {
        return null;
    }

    private void addTopicFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        TopicFragment topicFragment =
            fragment == null ? TopicFragment.newInstance(PrefUtil.getMe(this).getLogin(), type)
                : (TopicFragment) fragment;
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
