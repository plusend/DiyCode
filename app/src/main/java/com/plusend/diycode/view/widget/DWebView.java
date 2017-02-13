package com.plusend.diycode.view.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.plusend.diycode.view.activity.WebActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DWebView extends WebView {
  private static final String TAG = "DWebView";

  public DWebView(Context context) {
    super(context);
    init();
  }

  public DWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DWebView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public DWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  public DWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
    super(context, attrs, defStyleAttr, privateBrowsing);
    init();
  }

  @SuppressLint({ "AddJavascriptInterface", "SetJavaScriptEnabled" }) private void init() {
    setClickable(false);
    setFocusable(false);

    setHorizontalScrollBarEnabled(false);

    WebSettings settings = getSettings();
    settings.setDefaultFontSize(14);
    settings.setSupportZoom(false);
    settings.setBuiltInZoomControls(false);
    settings.setDisplayZoomControls(false);
    settings.setJavaScriptEnabled(true);

    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
    //    addJavascriptInterface(new OnWebViewImageListener() {
    //        @Override
    //        @JavascriptInterface
    //        public void showImagePreview(String bigImageUrl) {
    //            if (bigImageUrl != null && !StringUtils.isEmpty(bigImageUrl)) {
    //                ImageGalleryActivity.show(getContext(), bigImageUrl);
    //            }
    //        }
    //    }, "mWebViewImageListener");
    //}
  }

  public void loadDetailDataAsync(final String content, Runnable finishCallback) {
    this.setWebViewClient(new DWebClient(finishCallback));
    Context context = getContext();
    if (context != null && context instanceof Activity) {
      final Activity activity = (Activity) context;
      AppOperator.runOnThread(new Runnable() {
        @Override public void run() {
          final String body = setupWebContent(content, true, true, "");
          activity.runOnUiThread(new Runnable() {
            @Override public void run() {
              //loadData(body, "text/html; charset=UTF-8", null);
              loadDataWithBaseURL("", body, "text/html", "UTF-8", "");
            }
          });
        }
      });
    } else {
      Log.e(TAG, "loadDetailDataAsync error, the Context isn't ok");
    }
  }

  public void loadTweetDataAsync(final String content, Runnable finishCallback) {
    this.setWebViewClient(new DWebClient(finishCallback));
    Context context = getContext();
    if (context != null && context instanceof Activity) {
      final Activity activity = (Activity) context;
      AppOperator.runOnThread(new Runnable() {
        @Override public void run() {
          final String body = setupWebContent(content, "");
          activity.runOnUiThread(new Runnable() {
            @Override public void run() {
              //loadData(body, "text/html; charset=UTF-8", null);
              loadDataWithBaseURL("", body, "text/html", "UTF-8", "");
            }
          });
        }
      });
    } else {
      Log.e(TAG, "loadDetailDataAsync error, the Context isn't ok");
    }
  }

  @Override public void destroy() {
    setWebViewClient(null);

    WebSettings settings = getSettings();
    settings.setJavaScriptEnabled(false);

    removeJavascriptInterface("mWebViewImageListener");
    removeAllViewsInLayout();

    removeAllViews();
    //clearCache(true);

    super.destroy();
  }

  private static String setupWebContent(String content, String style) {
    if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim())) return "";
    //if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
    //        || TDevice.isWifiOpen()) {
    Pattern pattern = Pattern.compile(
        "<img[^>]+src\\s*=\\s*[\"\']([^\"\']*)[\"\'](\\s*data-url\\s*=\\s*[\"\']([^\"\']*)[\"\'])*");
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
      String snippet = String.format(
          "<img style=\"vertical-align: bottom;\" src=\"%s\" onClick=\"javascript:mWebViewImageListener.showImagePreview('%s')\"",
          matcher.group(1), matcher.group(3) == null ? matcher.group(1) : matcher.group(3));
      content = content.replace(matcher.group(0), snippet);
    }
    //} else {
    //    // 过滤掉 img标签
    //    content = content.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
    //}
    return String.format("<!DOCTYPE html>"
        + "<html>"
        + "<head>"
        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common_new.css\">"
        + "</head>"
        + "<body>"
        + "<div class='contentstyle' id='article_id' style='%s'>"
        + "%s"
        + "</div>"
        + "</body>"
        + "</html>", style == null ? "" : style, content);
  }

  private static String setupWebContent(String content, boolean isShowHighlight,
      boolean isShowImagePreview, String css) {
    if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim())) return "";

    // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
    //if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
    //        || TDevice.isWifiOpen()) {
    // 过滤掉 img标签的width,height属性
    content = content.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
    content = content.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");

    // 添加点击图片放大支持
    if (isShowImagePreview) {
      // TODO 用一个正则就搞定??
      content = content.replaceAll("<img[^>]+src=\"([^\"\'\\s]+)\"[^>]*>",
          "<img src=\"$1\" onClick=\"javascript:mWebViewImageListener.showImagePreview('$1')\"/>");
      content = content.replaceAll(
          "<a\\s+[^<>]*href=[\"\']([^\"\']+)[\"\'][^<>]*>\\s*<img\\s+src=\"([^\"\']+)\"[^<>]*/>\\s*</a>",
          "<a href=\"$1\"><img src=\"$2\"/></a>");
    }
    //} else {
    //    // 过滤掉 img标签
    //    content = content.replaceAll("<\\s*img\\s+([^>]*)\\s*/>", "");
    //}

    // 过滤table的内部属性
    content = content.replaceAll("(<table[^>]*?)\\s+border\\s*=\\s*\\S+", "$1");
    content = content.replaceAll("(<table[^>]*?)\\s+cellspacing\\s*=\\s*\\S+", "$1");
    content = content.replaceAll("(<table[^>]*?)\\s+cellpadding\\s*=\\s*\\S+", "$1");

    return String.format("<!DOCTYPE html>"
        + "<html><head>"
        + (isShowHighlight
        ? "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/html/css/front.css\">"
        : "")
        + (isShowHighlight
        ? "<script type=\"text/javascript\" src=\"file:///android_asset/html/js/d3.min.js\"></script>"
        : "")
        + "%s"
        + "</head>"
        + "<body data-controller-name=\"topics\">"
        + "<div class=\"row\"><div class=\"col-md-9\"><div class=\"topic-detail panel panel-default\"><div class=\"panel-body markdown\">"
        + "<article>"
        + "%s"
        + "</article>"
        + "</div></div></div></div>"
        + "</body></html>", (css == null ? "" : css), content);
  }

  private static class DWebClient extends WebViewClient implements Runnable {
    private Runnable mFinishCallback;
    private boolean mDone = false;

    DWebClient(Runnable finishCallback) {
      super();
      mFinishCallback = finishCallback;
    }

    @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      mDone = false;
      // 当webview加载2秒后强制回馈完成
      view.postDelayed(this, 2800);
    }

    @Override public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      run();
    }

    @Override public synchronized void run() {
      if (!mDone) {
        mDone = true;
        if (mFinishCallback != null) mFinishCallback.run();
      }
    }

    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      Log.d(TAG, "shouldOverrideUrlLoading: " + url);
      Intent intent = new Intent(view.getContext(), WebActivity.class);
      intent.putExtra(WebActivity.URL, url);
      view.getContext().startActivity(intent);
      return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
      // TODO
      handler.cancel();
    }
  }
}
