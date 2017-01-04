package com.plusend.diycode.view.adapter.topic;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.topic.entity.TopicDetail;
import com.plusend.diycode.mvp.model.topic.entity.TopicReply;
import com.plusend.diycode.util.GlideImageGetter;
import com.plusend.diycode.util.LinkMovementMethodExt;
import com.plusend.diycode.util.SpanClickListener;
import com.plusend.diycode.util.TimeUtil;
import com.plusend.diycode.util.ToastUtil;
import com.plusend.diycode.view.activity.CreateTopicReplyActivity;
import com.plusend.diycode.view.activity.UserActivity;
import com.plusend.diycode.view.activity.WebActivity;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
  private final Set<String> offlineResources = new HashSet<>();

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
        return new ReplyViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_topic_reply, parent, false));
      default:
        return null;
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ReplyViewHolder) {
      final ReplyViewHolder rHolder = (ReplyViewHolder) holder;
      position--;
      rHolder.topicDetail = topicDetail;
      rHolder.topicReply = topicReplyList.get(position);
      Log.v(TAG, "topicReply: " + rHolder.topicReply);
      rHolder.name.setText(topicReplyList.get(position).getUser().getLogin());
      String floor = String.format(Locale.CHINESE, "%d楼", position + 1);
      rHolder.position.setText(floor);
      Glide.with(rHolder.avatar.getContext())
          .load(topicReplyList.get(position).getUser().getAvatarUrl())
          .crossFade()
          .centerCrop()
          .into(rHolder.avatar);
      rHolder.content.setText(Html.fromHtml(topicReplyList.get(position).getBodyHtml(),
          new GlideImageGetter(rHolder.content.getContext(), rHolder.content), null));
      rHolder.content.setMovementMethod(new LinkMovementMethodExt(new SpanClickListener() {
        @Override public void onClick(int type, String url) {
          Log.d(TAG, "url: " + url);
          if (url.startsWith("/")) {
            // url: "/plusend"
            Log.d(TAG, "username: " + url.substring(1));
            Intent intent = new Intent(rHolder.content.getContext(), UserActivity.class);
            intent.putExtra(UserActivity.LOGIN_NAME, url.substring(1));
            rHolder.content.getContext().startActivity(intent);
          } else if (url.startsWith("#")) {
            // url: "#reply1"
            Log.d(TAG, "楼");
            // TODO
          } else {
            Intent intent = new Intent(rHolder.content.getContext(), WebActivity.class);
            intent.putExtra(WebActivity.URL, url);
            rHolder.content.getContext().startActivity(intent);
          }
        }
      }));
    } else if (holder instanceof HeaderReplyViewHolder) {
      if (topicDetail == null) {
        return;
      }
      final HeaderReplyViewHolder header = (HeaderReplyViewHolder) holder;
      header.topicDetail = topicDetail;
      header.name.setText(topicDetail.getUser().getLogin());
      header.time.setText(TimeUtil.computePastTime(topicDetail.getUpdatedAt()));
      header.title.setText(topicDetail.getTitle());
      Glide.with(header.avatar.getContext())
          .load(topicDetail.getUser().getAvatarUrl())
          .crossFade()
          .centerCrop()
          .into(header.avatar);
      //header.favorite.setSelected(isFavorited);
      //header.thumb.setSelected(isFollowed);
      header.topic.setText(topicDetail.getNodeName());
      header.repliesCount.setText("共收到 " + topicDetail.getRepliesCount() + " 条回复");
      if (offlineResources.isEmpty()) {
        fetchOfflineResources(header.content.getContext());
      }
      header.content.setWebViewClient(new WebViewClient() {
        public void onReceivedError(WebView view, int errorCode, String description,
            String failingUrl) {
          ToastUtil.showText(header.content.getContext(), "Oh no! " + description);
        }

        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
          Intent intent = new Intent(header.content.getContext(), WebActivity.class);
          intent.putExtra(WebActivity.URL, url);
          header.content.getContext().startActivity(intent);
          return true;
        }

        @Override public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
          Log.d(TAG, "shouldInterceptRequest thread id: " + Thread.currentThread().getId());
          int lastSlash = url.lastIndexOf("/");
          if (lastSlash != -1) {
            String suffix = url.substring(lastSlash + 1);
            if (offlineResources.contains(suffix)) {
              String mimeType;
              if (suffix.endsWith(".js")) {
                mimeType = "application/x-javascript";
              } else if (suffix.endsWith(".css")) {
                mimeType = "text/css";
              } else {
                mimeType = "text/html";
              }
              try {
                InputStream is = view.getContext().getAssets().open("offline_res/" + suffix);
                Log.i(TAG, "shouldInterceptRequest use offline resource for: " + url);
                return new WebResourceResponse(mimeType, "UTF-8", is);
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
          Log.i("shouldInterceptRequest", "load resource from internet, url: " + url);
          return super.shouldInterceptRequest(view, url);
        }
      });
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        header.content.getSettings()
            .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
      } else {
        header.content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
      }
      header.content.loadData(getHtmlData(topicDetail.getBodyHtml()), "text/html; charset=utf-8",
          "utf-8");
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

  private String getHtmlData(String bodyHTML) {
    String head = "<head>"
        +
        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
        +
        "<link rel=\"stylesheet\" media=\"screen\" href=\"https://diycode.b0.upaiyun.com/assets/front.css\" data-turbolinks-track=\"true\" />"
        +
        "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
        +
        "</head>";
    return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
  }

  private void fetchOfflineResources(Context context) {
    AssetManager am = context.getApplicationContext().getAssets();
    try {
      String[] res = am.list("offline_res");
      if (res != null) {
        Collections.addAll(offlineResources, res);
      }
    } catch (IOException e) {
      e.printStackTrace();
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
    //@BindView(R.id.thumb) ImageView thumb;
    @BindView(R.id.reply) ImageView reply;
    @BindView(R.id.content) TextView content;
    private TopicReply topicReply;
    private TopicDetail topicDetail;

    ReplyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);

      reply.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(view.getContext(), CreateTopicReplyActivity.class);
          intent.putExtra(CreateTopicReplyActivity.TOPIC_ID, topicDetail.getId());
          intent.putExtra(CreateTopicReplyActivity.TOPIC_TITLE, topicDetail.getTitle());
          // #2楼 @plusend
          intent.putExtra(CreateTopicReplyActivity.TO,
              "#" + position.getText().toString() + " @" + topicReply.getUser().getLogin() + " ");
          view.getContext().startActivity(intent);
        }
      });

      View.OnClickListener listener = new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(view.getContext(), UserActivity.class);
          intent.putExtra(UserActivity.LOGIN_NAME, topicReply.getUser().getLogin());
          view.getContext().startActivity(intent);
        }
      };
      avatar.setOnClickListener(listener);
      name.setOnClickListener(listener);
    }
  }

  static class HeaderReplyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.topic) TextView topic;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.content) WebView content;
    //@BindView(R.id.thumb) ImageView thumb;
    //@BindView(R.id.favorite) ImageView favorite;
    @BindView(R.id.replies_count) TextView repliesCount;
    private TopicDetail topicDetail;

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

      View.OnClickListener listener = new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(view.getContext(), UserActivity.class);
          intent.putExtra(UserActivity.LOGIN_NAME, topicDetail.getUser().getLogin());
          view.getContext().startActivity(intent);
        }
      };
      avatar.setOnClickListener(listener);
      name.setOnClickListener(listener);
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
