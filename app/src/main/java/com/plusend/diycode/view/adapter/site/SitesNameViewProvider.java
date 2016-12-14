package com.plusend.diycode.view.adapter.site;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.plusend.diycode.R;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by plusend on 2016/12/13.
 */
public class SitesNameViewProvider
    extends ItemViewProvider<SitesName, SitesNameViewProvider.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_sites_name, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SitesName sitesName) {
    holder.sitesName = sitesName;
    holder.title.setText(sitesName.getName());
    Glide.with(holder.icon.getContext())
        .load(sitesName.getAvatarUrl())
        .crossFade()
        .into(holder.icon);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.icon) ImageView icon;
    @BindView(R.id.title) TextView title;
    private SitesName sitesName;
    private Context context;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      context = itemView.getContext();

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Uri uri = Uri.parse(sitesName.getUrl());
          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          context.startActivity(intent);
        }
      });
    }
  }
}