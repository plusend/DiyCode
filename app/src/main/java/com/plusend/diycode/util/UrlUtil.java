package com.plusend.diycode.util;

import android.util.Patterns;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by plusend on 2016/12/4.
 */

public class UrlUtil {
  /**
   * 获取 URL 的 Host
   */
  public static String getHost(String urlString) {
    String result = urlString;
    try {
      URL url = new URL(urlString);
      result = url.getHost();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    return result;
  }

  /**
   * 提取文本中的链接
   */
  public static String getUrl(String text) {
    Pattern p = Patterns.WEB_URL;
    Matcher matcher = p.matcher(text);
    if (matcher.find()) {
      return matcher.group();
    } else {
      return null;
    }
  }
}
