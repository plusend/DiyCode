package com.plusend.diycode.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.entity.News;
import com.plusend.diycode.mvp.presenter.NewsPresenter;
import com.plusend.diycode.mvp.view.NewsView;
import com.plusend.diycode.view.adapter.DividerListItemDecoration;
import com.plusend.diycode.view.adapter.EmptyRecyclerView;
import com.plusend.diycode.view.adapter.NewsAdapter;
import com.plusend.diycode.view.adapter.topic.TopicsAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plusend on 2016/11/22.
 */

public class NewsFragment extends Fragment implements NewsView {
  private static final String TAG = "NewsFragment";

  @BindView(R.id.rv_news) EmptyRecyclerView rv;
  @BindView(R.id.empty_view) TextView emptyView;
  private List<News> newsList = new ArrayList<>();
  private NewsAdapter newsAdapter;
  private LinearLayoutManager linearLayoutManager;
  private NewsPresenter newsPresenter;
  private int offset;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");
    View rootView = inflater.inflate(R.layout.fragment_news, container, false);
    ButterKnife.bind(this, rootView);
    linearLayoutManager = new LinearLayoutManager(this.getContext());
    rv.setLayoutManager(linearLayoutManager);
    rv.setEmptyView(emptyView);
    newsAdapter = new NewsAdapter(newsList);
    rv.setAdapter(newsAdapter);
    rv.addItemDecoration(new DividerListItemDecoration(getContext()));
    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == newsAdapter.getItemCount()) {
          newsAdapter.setStatus(NewsAdapter.STATUS_LOADING);
          newsAdapter.notifyDataSetChanged();
          newsPresenter.readNews(offset);
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });
    newsPresenter = new NewsPresenter(this);
    return rootView;
  }

  @Override public void showNews(List<News> newsList) {
    Log.v(TAG, "newsList: " + newsList);
    if (newsList == null) {
      return;
    }
    this.newsList.addAll(newsList);
    offset = this.newsList.size();
    if (newsList.isEmpty()) {
      newsAdapter.setStatus(TopicsAdapter.STATUS_NO_MORE);
    } else {
      newsAdapter.setStatus(TopicsAdapter.STATUS_NORMAL);
    }
    newsAdapter.notifyDataSetChanged();
  }

  @Override public void onStart() {
    super.onStart();
    //newsAdapter.setOnItemClickListener(
    //    new TopicRecyclerViewAdapter.OnItemClickListener() {
    //      @Override public void onItemClick(View view, int position) {
    //        Intent intent = new Intent(getActivity(), TopicActivity.class);
    //        intent.putExtra(TopicActivity.ID, newsList.get(position).getId());
    //        startActivity(intent);
    //      }
    //    });

    newsPresenter.start();
    newsPresenter.readNews(offset);
  }

  @Override public void onStop() {
    newsPresenter.stop();
    super.onStop();
  }
}
