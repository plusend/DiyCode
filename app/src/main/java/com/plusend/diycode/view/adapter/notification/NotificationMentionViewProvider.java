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

public class NotificationMentionViewProvider
    extends ItemViewProvider<NotificationMention, NotificationMentionViewProvider.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_notification_mention, parent, false);
    return new ViewHolder(root);
  }

  @Override protected void onBindViewHolder(@NonNull ViewHolder holder,
      @NonNull NotificationMention notificationMention) {
    holder.mention = notificationMention;
    Glide.with(holder.avatar.getContext())
        .load(notificationMention.getAvatarUrl().replace("large_", ""))
        .crossFade()
        .centerCrop()
        .into(holder.avatar);
    String header = notificationMention.getLogin()
        + "<font color='#9e9e9e'> 在 </font>"
        + notificationMention.getTopicTitle()
        + "<font color='#9e9e9e'> 提及你：</font>";
    holder.header.setText(Html.fromHtml(header));
    holder.body.setText(Html.fromHtml(notificationMention.getBodyHtml(),
        new GlideImageGetter(holder.body.getContext(), holder.body), null));
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.header) TextView header;
    @BindView(R.id.body) TextView body;

    private NotificationMention mention;
    private Context context;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      context = itemView.getContext();
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(context, TopicActivity.class);
          intent.putExtra(TopicActivity.ID, mention.getTopicId());
          context.startActivity(intent);
        }
      });
    }
  }
}