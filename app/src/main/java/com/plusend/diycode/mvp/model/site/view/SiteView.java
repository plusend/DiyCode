package com.plusend.diycode.mvp.model.site.view;

import com.plusend.diycode.mvp.model.site.entity.Site;
import com.plusend.diycode.mvp.model.base.BaseView;
import java.util.List;

/**
 * Created by plusend on 2016/11/27.
 */

public interface SiteView extends BaseView {
  void showSite(List<Site> siteList);
}
