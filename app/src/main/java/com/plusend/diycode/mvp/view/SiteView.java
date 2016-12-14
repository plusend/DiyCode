package com.plusend.diycode.mvp.view;

import com.plusend.diycode.mvp.model.entity.Site;
import java.util.List;

/**
 * Created by plusend on 2016/11/27.
 */

public interface SiteView extends BaseView {
  void showSite(List<Site> siteList);
}
