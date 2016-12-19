package com.plusend.diycode.view.adapter.topic;

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
import com.plusend.diycode.mvp.model.entity.Topic;
import com.plusend.diycode.util.TimeUtil;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class TopicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String TAG = "TopicRVAdapter";
  private static final int ITEM_NORMAL = 1;
  private static final int ITEM_FOOTER = 2;
  public static final int STATUS_NORMAL = 1;//正常状态
  public static final int STATUS_LOADING = 2;//正在加载中
  public static final int STATUS_NO_MORE = 3;//没有更多了
  private int status;

  private List<Topic> topicList;

  public TopicsAdapter(List<Topic> topicList) {
    this.topicList = topicList;
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
        return new TopicViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
      case ITEM_FOOTER:
        return new FooterViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_topic_footer, parent, false));
      default:
        return new TopicViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof TopicViewHolder) {
      TopicViewHolder topicViewHolder = (TopicViewHolder) holder;
      topicViewHolder.name.setText(topicList.get(position).getUser().getLogin());
      topicViewHolder.topic.setText(topicList.get(position).getNodeName());
      topicViewHolder.title.setText(topicList.get(position).getTitle());
      if (topicList.get(position).getRepliedAt() != null) {
        topicViewHolder.time.setText(
            TimeUtil.computePastTime(topicList.get(position).getRepliedAt()));
      } else {
        topicViewHolder.time.setText(
            TimeUtil.computePastTime(topicList.get(position).getCreatedAt()));
      }
      Glide.with(topicViewHolder.avatar.getContext())
          .load(topicList.get(position).getUser().getAvatarUrl())
          .crossFade()
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
    if (topicList == null || topicList.isEmpty()) {
      return 0;
    } else {
      return topicList.size() + 1;
    }
  }

  public void setStatus(int status) {
    this.status = status;
  }

  private static OnItemClickListener mOnItemClickListener;

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  static class TopicViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.topic) TextView topic;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.title) TextView title;
    //@BindView(R.id.content) TextView content;
    //@BindView(R.id.image) ImageView image;
    //@BindView(R.id.thumb) ImageView thumb;
    //@BindView(R.id.favorite) ImageView favorite;

    TopicViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);

      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, getAdapterPosition());
          }
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
