package com.plusend.diycode.view.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.plusend.diycode.R;

/**
 * Created by plusend on 2016/12/4.
 */

public class DividerListItemDecoration extends RecyclerView.ItemDecoration {

  private Drawable divider;

  public DividerListItemDecoration(Context context) {
    divider = context.getResources().getDrawable(R.drawable.item_divider_vertical);
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.set(0, 0, 0, divider.getIntrinsicHeight());
  }
}
