package com.plusend.diycode.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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
import com.plusend.diycode.mvp.model.entity.TopicDetail;
import com.plusend.diycode.mvp.model.entity.TopicReply;
import com.plusend.diycode.util.GlideImageGetter;
import com.plusend.diycode.util.LinkMovementMethodExt;
import com.plusend.diycode.util.SpanClickListener;
import com.plusend.diycode.util.TimeUtil;
import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class TopicRepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String TAG = "TopicReplyAdapter";
  private static final int ITEM_HEADER = 1;
  private static final int ITEM_REPLY = 2;
  private static final int ITEM_FOOTER = 3;
  public static final int STATUS_NORMAL = 1;//正常状态
  public static final int STATUS_LOADING = 2;//正在加载中
  public static final int STATUS_NO_MORE = 3;//没有更多了
  private int status;
  private boolean isFollowed;
  private boolean isFavorited;

  private List<TopicReply> topicReplyList;
  private TopicDetail topicDetail;

  public TopicRepliesAdapter(List<TopicReply> topicReplyList, TopicDetail topicDetail) {
    this.topicReplyList = topicReplyList;
    this.topicDetail = topicDetail;
  }

  public void setTopicDetail(TopicDetail topicDetail) {
    this.topicDetail = topicDetail;
  }

  @Override public int getItemViewType(int position) {
    if (position == 0) {
      return ITEM_HEADER;
    } else if (position + 1 == getItemCount()) {
      return ITEM_FOOTER;
    } else {
      return ITEM_REPLY;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ITEM_HEADER:
        return new HeaderReplyViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_fragment_topic_header, parent, false));
      case ITEM_FOOTER:
        return new FooterViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_topic_footer, parent, false));
      case ITEM_REPLY:
        return new ReplyViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_reply, parent, false));
      default:
        return null;
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ReplyViewHolder) {
      final ReplyViewHolder rHolder = (ReplyViewHolder) holder;
      position--;
      rHolder.name.setText(topicReplyList.get(position).getUser().getName());
      rHolder.position.setText((position + 1) + "楼");
      Glide.with(rHolder.avatar.getContext())
          .load(topicReplyList.get(position).getUser().getAvatarUrl())
          .crossFade()
          .into(rHolder.avatar);
      rHolder.content.setText(Html.fromHtml(topicReplyList.get(position).getBodyHtml(),
          new GlideImageGetter(rHolder.content.getContext(), rHolder.content), null));
      rHolder.content.setMovementMethod(new LinkMovementMethodExt(new SpanClickListener() {
        @Override public void onClick(int type, String url) {
          Log.d(TAG, "url: " + url);
          if (url.startsWith("/")) {
            // TODO goto user's page
            // url: "/plusend"
          }
          Uri uri = Uri.parse(url);
          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          rHolder.content.getContext().startActivity(intent);
        }
      }));
    } else if (holder instanceof HeaderReplyViewHolder) {
      if (topicDetail == null) {
        return;
      }
      final HeaderReplyViewHolder header = (HeaderReplyViewHolder) holder;
      header.name.setText(topicDetail.getUser().getLogin());
      header.time.setText(TimeUtil.computePastTime(topicDetail.getUpdatedAt()));
      header.title.setText(topicDetail.getTitle());
      Glide.with(header.avatar.getContext())
          .load(topicDetail.getUser().getAvatarUrl())
          .crossFade()
          .into(header.avatar);
      //header.favorite.setSelected(isFavorited);
      //header.thumb.setSelected(isFollowed);
      header.topic.setText(topicDetail.getNodeName());
      header.repliesCount.setText("共收到 " + topicDetail.getRepliesCount() + " 条回复");
      header.content.setText(Html.fromHtml(topicDetail.getBodyHtml(),
          new GlideImageGetter(header.content.getContext(), header.content), null));
      header.content.setMovementMethod(new LinkMovementMethodExt(new SpanClickListener() {
        @Override public void onClick(int type, String url) {
          Uri uri = Uri.parse(url);
          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          header.content.getContext().startActivity(intent);
        }
      }));
      //header.content.loadDataWithBaseURL(null, topicDetail.getBodyHtml(),
      //    "text/html", "utf-8", null);

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
    if (topicDetail == null) {
      return 0;
    }
    return topicReplyList == null ? 2 : topicReplyList.size() + 2;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  private static View.OnClickListener mOnFavoriteItemClickListener;

  public void setOnFavoriteItemClickListener(View.OnClickListener onFavoriteItemClickListener) {
    mOnFavoriteItemClickListener = onFavoriteItemClickListener;
  }

  static class ReplyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.position) TextView position;
    @BindView(R.id.thumb) ImageView thumb;
    @BindView(R.id.reply) ImageView reply;
    @BindView(R.id.content) TextView content;

    ReplyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  static class HeaderReplyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.topic) TextView topic;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.content) TextView content;
    //@BindView(R.id.thumb) ImageView thumb;
    //@BindView(R.id.favorite) ImageView favorite;
    @BindView(R.id.replies_count) TextView repliesCount;

    HeaderReplyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);

      //favorite.setOnClickListener(new View.OnClickListener() {
      //  @Override public void onClick(View view) {
      //    if (mOnFavoriteItemClickListener != null) {
      //      mOnFavoriteItemClickListener.onClick(view);
      //    }
      //  }
      //});
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

  public void setFollow(boolean isFollowed) {
    this.isFollowed = isFollowed;
    notifyDataSetChanged();
  }

  public void setFavorite(boolean isFavorited) {
    this.isFavorited = isFavorited;
    notifyDataSetChanged();
  }
}
