package com.plusend.diycode.view.adapter.site;

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

/**
 * Created by plusend on 2016/12/13.
 */
public class SiteNameViewProvider
    extends ItemViewProvider<SiteName, SiteNameViewProvider.ViewHolder> {

    @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
        @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_site_name, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SiteName siteName) {
        holder.title.setText(siteName.getName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}