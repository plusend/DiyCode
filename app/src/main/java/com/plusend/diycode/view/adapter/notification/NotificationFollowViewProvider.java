package com.plusend.diycode.view.adapter.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.plusend.diycode.R;
import com.plusend.diycode.util.IntentUtil;
import me.drakeet.multitype.ItemViewProvider;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class NotificationFollowViewProvider
    extends ItemViewProvider<NotificationFollow, NotificationFollowViewProvider.ViewHolder> {

    @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
        @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_notification_follow, parent, false);
        return new ViewHolder(root);
    }

    @Override protected void onBindViewHolder(@NonNull ViewHolder holder,
        @NonNull final NotificationFollow notificationFollow) {
        Glide.with(holder.avatar.getContext())
            .load(notificationFollow.getAvatarUrl())
            .apply(new RequestOptions().placeholder(R.mipmap.ic_avatar_error)
                .error(R.mipmap.ic_avatar_error)
                .transforms(new CenterCrop(), new RoundedCorners(20)))
            .transition(withCrossFade())
            .into(holder.avatar);
        holder.login.setText(notificationFollow.getLogin());
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override public void onClick(View v) {
                IntentUtil.startUserActivity(v.getContext(), notificationFollow.getLogin());
            }
        };
        holder.avatar.setOnClickListener(onClickListener);
        holder.login.setOnClickListener(onClickListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar) ImageView avatar;
        @BindView(R.id.login) TextView login;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}