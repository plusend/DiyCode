package com.plusend.diycode.view.adapter.notification;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.plusend.diycode.view.activity.TopicActivity;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by plusend on 2016/12/13.
 */
public class NotificationReplyViewProvider
    extends ItemViewProvider<NotificationReply, NotificationReplyViewProvider.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_notification_reply, parent, false);
    return new ViewHolder(root);
  }

  @Override protected void onBindViewHolder(@NonNull ViewHolder holder,
      @NonNull NotificationReply notificationReply) {
    holder.reply = notificationReply;
    Glide.with(holder.avatar.getContext())
        .load(notificationReply.getAvatarUrl())
        .into(holder.avatar);
    String header = notificationReply.getLogin()
        + "<font color='#9e9e9e'> 在帖子 </font>"
        + notificationReply.getTopicTitle()
        + "<font color='#9e9e9e'> 回复了：</font>";
    holder.header.setText(Html.fromHtml(header));
    holder.body.setText(Html.fromHtml(notificationReply.getBodyHtml(),
        new GlideImageGetter(holder.body.getContext(), holder.body), null));
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.header) TextView header;
    @BindView(R.id.body) TextView body;

    private NotificationReply reply;
    private Context context;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      context = itemView.getContext();

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(context, TopicActivity.class);
          intent.putExtra(TopicActivity.ID, reply.getTopicId());
          context.startActivity(intent);
        }
      });
    }
  }
}