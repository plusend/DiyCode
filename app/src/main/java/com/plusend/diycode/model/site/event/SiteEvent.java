package com.plusend.diycode.model.site.event;

import com.plusend.diycode.model.site.entity.Site;
import java.util.List;

/**
 * Created by plusend on 2016/11/27.
 */

public class SiteEvent {
  private List<Site> siteList;

  public SiteEvent(List<Site> siteList) {
    this.siteList = siteList;
  }

  public List<Site> getSiteList() {
    return siteList;
  }
}
