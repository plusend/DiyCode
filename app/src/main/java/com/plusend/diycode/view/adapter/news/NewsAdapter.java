package com.plusend.diycode.view.adapter.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.plusend.diycode.R;
import com.plusend.diycode.model.news.entity.News;
import com.plusend.diycode.util.TimeUtil;
import com.plusend.diycode.util.UrlUtil;
import com.plusend.diycode.view.activity.WebActivity;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String TAG = "NewsAdapter";
  private static final int ITEM_NORMAL = 1;
  private static final int ITEM_FOOTER = 2;
  public static final int STATUS_NORMAL = 1;//正常状态
  public static final int STATUS_LOADING = 2;//正在加载中
  public static final int STATUS_NO_MORE = 3;//没有更多了
  private int status;
  private List<News> newsList;

  public NewsAdapter(List<News> newsList) {
    this.newsList = newsList;
  }

  @Override public int getItemViewType(int position) {
    if (position + 1 == getItemCount()) {
      return ITEM_FOOTER;
    } else {
      return ITEM_NORMAL;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ITEM_NORMAL:
        return new NewsViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
      case ITEM_FOOTER:
        return new FooterViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_topic_footer, parent, false));
      default:
        return new NewsViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof NewsViewHolder) {
      NewsViewHolder topicViewHolder = (NewsViewHolder) holder;
      topicViewHolder.news = newsList.get(position);
      topicViewHolder.name.setText(newsList.get(position).getUser().getLogin());
      topicViewHolder.topic.setText(newsList.get(position).getNodeName());
      topicViewHolder.title.setText(newsList.get(position).getTitle());
      if (newsList.get(position).getRepliedAt() != null) {
        topicViewHolder.time.setText(
            TimeUtil.computePastTime(newsList.get(position).getUpdatedAt()));
      } else {
        topicViewHolder.time.setText(
            TimeUtil.computePastTime(newsList.get(position).getCreatedAt()));
      }
      topicViewHolder.host.setText(UrlUtil.getHost(newsList.get(position).getAddress()));
      Glide.with(topicViewHolder.avatar.getContext())
          .load(newsList.get(position).getUser().getAvatarUrl())
          .placeholder(R.mipmap.ic_avatar_error)
          .error(R.mipmap.ic_avatar_error)
          .crossFade()
          .centerCrop()
          .into(topicViewHolder.avatar);
    } else if (holder instanceof FooterViewHolder) {
      FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
      switch (status) {
        case STATUS_NORMAL:
          footerViewHolder.tips.setText("上拉加载更多");
          footerViewHolder.progressBar.setVisibility(View.GONE);
          break;
        case STATUS_LOADING:
          footerViewHolder.tips.setText("加载中");
          footerViewHolder.progressBar.setVisibility(View.VISIBLE);
          break;
        case STATUS_NO_MORE:
          footerViewHolder.tips.setText("没有更多了");
          footerViewHolder.progressBar.setVisibility(View.GONE);
          break;
        default:
          break;
      }
    }
  }

  @Override public int getItemCount() {
    if (newsList == null || newsList.isEmpty()) {
      return 0;
    } else {
      return newsList.size() + 1;
    }
  }

  public void setStatus(int status) {
    this.status = status;
  }

  static class NewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.topic) TextView topic;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.host) TextView host;
    //@BindView(R.id.content) TextView content;
    //@BindView(R.id.image) ImageView image;
    //@BindView(R.id.thumb) ImageView thumb;
    //@BindView(R.id.favorite) ImageView favorite;
    private News news;
    private Context context;

    NewsViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      context = view.getContext();

      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(context, WebActivity.class);
          intent.putExtra(WebActivity.URL, news.getAddress());
          context.startActivity(intent);
        }
      });
    }
  }

  static class FooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tips) TextView tips;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    FooterViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
