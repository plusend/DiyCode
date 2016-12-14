package com.plusend.diycode.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.plusend.diycode.view.fragment.NewsFragment;
import com.plusend.diycode.view.fragment.SitesFragment;
import com.plusend.diycode.view.fragment.TopicFragment;

/**
 * Created by plusend on 2016/11/22.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
  public MainPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return TopicFragment.newInstance("", TopicFragment.TYPE_ALL);
      case 1:
        return new NewsFragment();
      case 2:
        return new SitesFragment();
      default:
        return null;
    }
  }

  @Override public int getCount() {
    return 3;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "topics";
      case 1:
        return "news";
      case 2:
        return "sites";
      default:
        return null;
    }
  }
}
