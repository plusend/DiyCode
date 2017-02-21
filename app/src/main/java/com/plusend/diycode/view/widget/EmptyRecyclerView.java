package com.plusend.diycode.view.widget;

/**
 * Created by plusend on 2016/12/18.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class EmptyRecyclerView extends RecyclerView {
  @Nullable View emptyView;

  public EmptyRecyclerView(Context context) {
    super(context);
  }

  public EmptyRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  void checkIfEmpty() {
    if (emptyView != null && getAdapter() != null) {
      emptyView.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
      this.setVisibility(getAdapter().getItemCount() > 0 ? VISIBLE : GONE);
    }
  }

  final @NonNull AdapterDataObserver observer = new AdapterDataObserver() {

    @Override public void onChanged() {
      super.onChanged();
      checkIfEmpty();
    }

    @Override public void onItemRangeInserted(int positionStart, int itemCount) {
      super.onItemRangeInserted(positionStart, itemCount);
      checkIfEmpty();
    }

    @Override public void onItemRangeRemoved(int positionStart, int itemCount) {
      super.onItemRangeRemoved(positionStart, itemCount);
      checkIfEmpty();
    }
  };

  @Override public void setAdapter(@Nullable Adapter adapter) {
    final Adapter oldAdapter = getAdapter();
    if (oldAdapter != null) {
      oldAdapter.unregisterAdapterDataObserver(observer);
    }
    super.setAdapter(adapter);
    if (adapter != null) {
      adapter.registerAdapterDataObserver(observer);
    }
  }

  public void setEmptyView(@Nullable View emptyView) {
    this.emptyView = emptyView;
    checkIfEmpty();
  }
}
