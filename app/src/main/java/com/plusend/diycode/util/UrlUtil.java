package com.plusend.diycode.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by plusend on 2016/12/4.
 */

public class UrlUtil {
  /**
   * 获取 URL 的 Host
   * @param urlString
   * @return
   */
  public static String getHost(String urlString){
    String result = urlString;
    try {
      URL url = new URL(urlString);
      result = url.getHost();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    return result;
  }
}
