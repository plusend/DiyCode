package com.plusend.diycode.view.adapter.reply;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.topic.entity.Reply;
import com.plusend.diycode.util.GlideImageGetter;
import com.plusend.diycode.util.TimeUtil;
import com.plusend.diycode.view.activity.TopicActivity;
import me.drakeet.multitype.ItemViewProvider;

public class ReplyViewProvider extends ItemViewProvider<Reply, ReplyViewProvider.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_reply, parent, false);
    return new ViewHolder(root);
  }

  @Override protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Reply reply) {
    holder.reply = reply;
    holder.topicTitle.setText(Html.fromHtml("<font color='#ff0099cc'> 在帖子 </font>"
        + reply.getTopicTitle()
        + "<font color='#ff0099cc'> 发表了回复：</font>"));
    holder.body.setText(Html.fromHtml(reply.getBodyHtml(),
        new GlideImageGetter(holder.body.getContext(), holder.body), null));
    holder.time.setText(TimeUtil.formatTime(reply.getUpdatedAt()));
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.topic_title) TextView topicTitle;
    @BindView(R.id.body) TextView body;
    @BindView(R.id.time) TextView time;
    private Context context;
    private Reply reply;

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