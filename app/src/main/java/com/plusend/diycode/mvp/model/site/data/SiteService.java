package com.plusend.diycode.mvp.model.site.data;

import com.plusend.diycode.mvp.model.site.entity.Site;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

interface SiteService {

  /**
   * 获取酷站信息
   */
  @GET("sites.json") Call<List<Site>> getSite();
}
