package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import com.plusend.diycode.model.topic.entity.Reply;
import com.plusend.diycode.model.topic.presenter.RepliesBasePresenter;
import com.plusend.diycode.model.topic.view.RepliesView;
import com.plusend.diycode.util.PrefUtil;
import com.plusend.diycode.util.ToastUtil;
import com.plusend.diycode.view.widget.DividerListItemDecoration;
import com.plusend.diycode.view.widget.EmptyRecyclerView;
import com.plusend.diycode.view.adapter.reply.ReplyViewProvider;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MyRepliesActivity extends BaseActivity implements RepliesView {
  private static final String TAG = "MyRepliesActivity";
  public static final String LOGIN_NAME = "loginName";

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.rv_replies) EmptyRecyclerView rvReplies;
  @BindView(R.id.empty_view) TextView emptyView;

  private MultiTypeAdapter adapter;
  private LinearLayoutManager linearLayoutManager;
  private Items items;
  private RepliesBasePresenter presenter;
  private int offset;
  private String loginName;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_my_replies);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    presenter = new RepliesBasePresenter(this);
    items = new Items();
    adapter = new MultiTypeAdapter(items);
    adapter.register(Reply.class, new ReplyViewProvider());
    linearLayoutManager = new LinearLayoutManager(this);
    rvReplies.setLayoutManager(linearLayoutManager);
    rvReplies.setAdapter(adapter);
    rvReplies.setEmptyView(emptyView);
    rvReplies.addItemDecoration(new DividerListItemDecoration(getContext()));
    rvReplies.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          //adapter.setStatus(TopicsAdapter.STATUS_LOADING);
          //adapter.notifyDataSetChanged();
          presenter.getReplies(loginName, offset);
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });

    loginName = PrefUtil.getMe(this).getLogin();
    if (TextUtils.isEmpty(loginName)) {
      startActivityForResult(new Intent(MyRepliesActivity.this, SignInActivity.class),
          SignInActivity.REQUEST_CODE);
      ToastUtil.showText(MyRepliesActivity.this, "请先登录");
    } else {
      getReplies();
    }
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<BasePresenter> getPresenter() {
    return super.addPresenter(presenter);
  }

  @Override public void showReplies(List<Reply> replyList) {
    if (replyList == null || replyList.isEmpty()) {
      return;
    }
    for (Reply reply : replyList) {
      items.add(reply);
    }
    offset = items.size();
    adapter.notifyDataSetChanged();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode == SignInActivity.RESULT_OK) {
          getReplies();
        } else {
          ToastUtil.showText(MyRepliesActivity.this, "放弃登录");
          finish();
        }
        break;
    }
  }

  private void getReplies() {
    loginName = PrefUtil.getMe(this).getLogin();
    presenter.getReplies(loginName, offset);
  }

  @Override public Context getContext() {
    return this;
  }
}
