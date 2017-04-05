package com.plusend.diycode.view.adapter.about;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.plusend.diycode.R;
import me.drakeet.multitype.ItemViewProvider;

public class CardViewProvider extends ItemViewProvider<Card, CardViewProvider.ViewHolder> {

    @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
        @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.about_page_item_card, parent, false);
        return new ViewHolder(root);
    }

    @Override protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Card card) {
        holder.content.setText(card.content);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}