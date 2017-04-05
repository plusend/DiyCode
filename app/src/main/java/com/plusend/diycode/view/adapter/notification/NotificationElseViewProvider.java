package com.plusend.diycode.view.adapter.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import me.drakeet.multitype.ItemViewProvider;

public class NotificationElseViewProvider
    extends ItemViewProvider<NotificationElse, NotificationElseViewProvider.ViewHolder> {

    @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
        @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_notification_else, parent, false);
        return new ViewHolder(root);
    }

    @Override protected void onBindViewHolder(@NonNull ViewHolder holder,
        @NonNull NotificationElse notificationElse) {
        holder.content.setText(notificationElse.getType());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content) TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}