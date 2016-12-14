package com.plusend.diycode.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.entity.Topic;
import com.plusend.diycode.mvp.presenter.TopicsPresenter;
import com.plusend.diycode.mvp.view.TopicsView;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.view.activity.TopicActivity;
import com.plusend.diycode.view.adapter.MyDecoration;
import com.plusend.diycode.view.adapter.TopicsAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plusend on 2016/11/22.
 */

public class TopicFragment extends Fragment implements TopicsView {
  private static final String TAG = "TopicFragment";

  public static final String TYPE = "type";
  public static final int TYPE_ALL = 1;
  public static final int TYPE_CREATE = 2;
  public static final int TYPE_FAVORITE = 3;

  @BindView(R.id.rv) RecyclerView rv;
  private List<Topic> mTopicList = new ArrayList<>();
  private TopicsAdapter topicsAdapter;
  private LinearLayoutManager linearLayoutManager;
  private TopicsPresenter topicsPresenter;
  private int offset = 0;
  private int type = 0;
  private String loginName;

  public static TopicFragment newInstance(String loginName, int type) {
    TopicFragment topicFragment = new TopicFragment();
    Bundle b = new Bundle();
    b.putString(Constant.User.LOGIN, loginName);
    b.putInt(TYPE, type);
    topicFragment.setArguments(b);
    return topicFragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");
    View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
    ButterKnife.bind(this, rootView);
    linearLayoutManager = new LinearLayoutManager(this.getContext());
    rv.setLayoutManager(linearLayoutManager);
    topicsAdapter = new TopicsAdapter(mTopicList);
    rv.setAdapter(topicsAdapter);
    rv.addItemDecoration(new MyDecoration(getContext()));
    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == topicsAdapter.getItemCount()) {
          topicsAdapter.setStatus(TopicsAdapter.STATUS_LOADING);
          topicsAdapter.notifyDataSetChanged();
          if (type == TYPE_ALL) {
            topicsPresenter.getTopics(offset);
          } else if (type == TYPE_CREATE) {
            topicsPresenter.getUserCreateTopics(loginName, offset);
          } else if (type == TYPE_FAVORITE) {
            topicsPresenter.getUserFavoriteTopics(loginName, offset);
          }
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });
    topicsPresenter = new TopicsPresenter(this);
    return rootView;
  }

  @Override public void showTopics(List<Topic> topicList) {

    if (topicList == null) {
      Log.v(TAG, "showTopics: null");
      return;
    }
    Log.v(TAG, "showTopics: " + topicList.size());
    //this.mTopicList.clear();
    this.mTopicList.addAll(topicList);
    offset = this.mTopicList.size();
    if (topicList.isEmpty()) {
      topicsAdapter.setStatus(TopicsAdapter.STATUS_NO_MORE);
    } else {
      topicsAdapter.setStatus(TopicsAdapter.STATUS_NORMAL);
    }
    topicsAdapter.notifyDataSetChanged();
  }

  @Override public void onStart() {
    super.onStart();
    topicsPresenter.start();
    topicsAdapter.setOnItemClickListener(new TopicsAdapter.OnItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), TopicActivity.class);
        intent.putExtra(TopicActivity.ID, mTopicList.get(position).getId());
        startActivity(intent);
      }
    });
    Bundle bundle = getArguments();
    if (bundle != null) {
      loginName = bundle.getString(Constant.User.LOGIN);
      type = bundle.getInt(TYPE);
      Log.d(TAG, "loginName: " + loginName + " type: " + type);
    }

    if (!TextUtils.isEmpty(loginName)) {
      if (type != TYPE_FAVORITE) {
        topicsPresenter.getUserCreateTopics(loginName, offset);
      } else {
        topicsPresenter.getUserFavoriteTopics(loginName, offset);
      }
    } else {
      topicsPresenter.getTopics(offset);
    }
  }

  @Override public void onStop() {
    if (topicsPresenter != null) {
      topicsPresenter.stop();
    }
    super.onStop();
  }
}
