package com.plusend.diycode.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.site.entity.Site;
import com.plusend.diycode.mvp.model.site.presenter.SitePresenter;
import com.plusend.diycode.mvp.model.site.view.SiteView;
import com.plusend.diycode.view.adapter.EmptyRecyclerView;
import com.plusend.diycode.view.adapter.site.SiteName;
import com.plusend.diycode.view.adapter.site.SiteNameViewProvider;
import com.plusend.diycode.view.adapter.site.SitesName;
import com.plusend.diycode.view.adapter.site.SitesNameViewProvider;
import java.util.List;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by plusend on 2016/11/22.
 */

public class SitesFragment extends Fragment implements SiteView {
  private static final String TAG = "SitesFragment";

  private final static int SPAN_COUNT = 2;
  @BindView(R.id.rv_site_category) EmptyRecyclerView rvSiteCategory;
  @BindView(R.id.empty_view) TextView emptyView;

  private SitePresenter sitePresenter;
  private Items items = new Items();
  private MultiTypeAdapter adapter;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_site, container, false);
    ButterKnife.bind(this, rootView);

    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return (items.get(position) instanceof SiteName) ? SPAN_COUNT : 1;
      }
    });
    rvSiteCategory.setLayoutManager(layoutManager);

    adapter = new MultiTypeAdapter(items);

    adapter.register(SiteName.class, new SiteNameViewProvider());
    adapter.register(SitesName.class, new SitesNameViewProvider());
    rvSiteCategory.setAdapter(adapter);
    rvSiteCategory.setEmptyView(emptyView);

    //rvSiteCategory.addItemDecoration(new DividerGridItemDecoration(getContext()));
    sitePresenter = new SitePresenter(this);

    return rootView;
  }

  @Override public void showSite(List<Site> siteList) {
    Log.v(TAG, "showSite: " + siteList);
    if (siteList == null) {
      return;
    }

    for (Site site : siteList) {
      items.add(new SiteName(site.getName(), site.getId()));
      for (Site.Sites sites : site.getSites()) {
        items.add(new SitesName(sites.getName(), sites.getUrl(), sites.getAvatarUrl()));
      }
    }

    adapter.notifyDataSetChanged();
  }

  @Override public void onStart() {
    super.onStart();
    sitePresenter.start();
  }

  @Override public void onStop() {
    sitePresenter.stop();
    super.onStop();
  }
}
