package com.plusend.diycode.view.adapter.topic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.plusend.diycode.R;
import com.plusend.diycode.util.GlideImageGetter;
import com.plusend.diycode.util.HtmlUtil;
import com.plusend.diycode.util.LinkMovementMethodExt;
import com.plusend.diycode.util.SpanClickListener;
import com.plusend.diycode.view.activity.CreateTopicReplyActivity;
import com.plusend.diycode.view.activity.UserActivity;
import com.plusend.diycode.view.activity.WebActivity;
import java.util.Locale;
import me.drakeet.multitype.ItemViewProvider;

public class TopicReplyViewProvider
    extends ItemViewProvider<TopicReplyWithTopic, TopicReplyViewProvider.ViewHolder> {
  private static final String TAG = "TopicReplyViewProvider";

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_topic_reply, parent, false);
    return new ViewHolder(root);
  }

  @Override protected void onBindViewHolder(@NonNull final ViewHolder holder,
      @NonNull final TopicReplyWithTopic topicReply) {
    holder.name.setText(topicReply.getTopicReply().getUser().getLogin());
    String floor = String.format(Locale.CHINESE, "%d楼", getPosition());
    holder.position.setText(floor);
    Glide.with(holder.avatar.getContext())
        .load(topicReply.getTopicReply().getUser().getAvatarUrl())
        .error(R.mipmap.ic_avatar_error)
        .crossFade()
        .centerCrop()
        .into(holder.avatar);
    holder.content.setText(Html.fromHtml(HtmlUtil.removeP(topicReply.getTopicReply().getBodyHtml()),
        new GlideImageGetter(holder.content.getContext(), holder.content), null));
    holder.content.setMovementMethod(new LinkMovementMethodExt(new SpanClickListener() {
      @Override public void onClick(int type, String url) {
        Log.d(TAG, "url: " + url);
        if (url.startsWith("/")) {
          // url: "/plusend"
          Log.d(TAG, "username: " + url.substring(1));
          Intent intent = new Intent(holder.content.getContext(), UserActivity.class);
          intent.putExtra(UserActivity.LOGIN_NAME, url.substring(1));
          holder.content.getContext().startActivity(intent);
        } else if (url.startsWith("#")) {
          // url: "#reply1"
          Log.d(TAG, "楼");
          // TODO
        } else {
          Intent intent = new Intent(holder.content.getContext(), WebActivity.class);
          intent.putExtra(WebActivity.URL, url);
          holder.content.getContext().startActivity(intent);
        }
      }
    }));

    holder.reply.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), CreateTopicReplyActivity.class);
        intent.putExtra(CreateTopicReplyActivity.TOPIC_ID, topicReply.getTopicDetail().getId());
        intent.putExtra(CreateTopicReplyActivity.TOPIC_TITLE,
            topicReply.getTopicDetail().getTitle());
        // #2楼 @plusend
        intent.putExtra(CreateTopicReplyActivity.TO,
            "#" + holder.position.getText().toString() + " @" + topicReply.getTopicReply()
                .getUser()
                .getLogin() + " ");
        view.getContext().startActivity(intent);
      }
    });

    View.OnClickListener listener = new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), UserActivity.class);
        intent.putExtra(UserActivity.LOGIN_NAME, topicReply.getTopicReply().getUser().getLogin());
        view.getContext().startActivity(intent);
      }
    };
    holder.avatar.setOnClickListener(listener);
    holder.name.setOnClickListener(listener);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.position) TextView position;
    @BindView(R.id.reply) ImageView reply;
    @BindView(R.id.content) TextView content;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}