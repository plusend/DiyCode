package com.plusend.diycode.view.adapter.about;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.plusend.diycode.R;
import com.plusend.diycode.view.activity.WebActivity;
import me.drakeet.multitype.ItemViewProvider;

/**
 * @author drakeet
 */
public class ContributorViewProvider
    extends ItemViewProvider<Contributor, ContributorViewProvider.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.about_page_item_contributor, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Contributor contributor) {
    holder.avatar.setImageResource(contributor.avatarResId);
    holder.name.setText(contributor.name);
    holder.desc.setText(contributor.desc);
    holder.url = contributor.url;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    ImageView avatar;
    TextView name;
    TextView desc;
    String url;

    ViewHolder(View itemView) {
      super(itemView);
      avatar = (ImageView) itemView.findViewById(R.id.avatar);
      name = (TextView) itemView.findViewById(R.id.name);
      desc = (TextView) itemView.findViewById(R.id.desc);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(view.getContext(), WebActivity.class);
          intent.putExtra(WebActivity.URL, url);
          view.getContext().startActivity(intent);
        }
      });
    }
  }
}