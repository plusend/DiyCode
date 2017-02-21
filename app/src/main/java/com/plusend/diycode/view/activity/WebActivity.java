package com.plusend.diycode.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.plusend.diycode.R;
import com.plusend.diycode.model.base.BasePresenter;
import java.util.List;

public class WebActivity extends BaseActivity {
  private static final String TAG = "WebActivity";
  public static final String URL = "url";
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.webview) WebView webView;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  private String mUrl;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_web);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    initWebView(webView);

    Intent intent = getIntent();
    mUrl = intent.getStringExtra(URL);
    Log.d(TAG, "mUrl: " + mUrl);
    toolbar.setTitle(mUrl);
    webView.loadUrl(mUrl);
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override protected List<BasePresenter> getPresenter() {
    return null;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      case R.id.action_browser:
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mUrl));
        startActivity(intent);
        break;
      case R.id.action_share:
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, toolbar.getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_web, menu);
    return super.onCreateOptionsMenu(menu);
  }

  public void initWebView(WebView webview) {
    WebSettings mWebSettings = webview.getSettings();
    mWebSettings.setSupportZoom(true);
    mWebSettings.setLoadWithOverviewMode(true);
    mWebSettings.setUseWideViewPort(true);
    mWebSettings.setDefaultTextEncodingName("utf-8");
    mWebSettings.setLoadsImagesAutomatically(true);

    //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
    mWebSettings.setJavaScriptEnabled(true);

    saveData(mWebSettings);

    newWin(mWebSettings);

    webview.setWebChromeClient(webChromeClient);
    webview.setWebViewClient(webViewClient);
  }

  @Override public void onPause() {
    super.onPause();
    webView.onPause();
    // 暂时删除，否则影响 DWebView 的使用
    //webView.pauseTimers(); //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
  }

  @Override public void onResume() {
    super.onResume();
    webView.onResume();
    //webView.resumeTimers();
  }

  /**
   * 多窗口的问题
   */
  private void newWin(WebSettings mWebSettings) {
    //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
    //然后 复写 WebChromeClient的onCreateWindow方法
    mWebSettings.setSupportMultipleWindows(false);
    mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
  }

  /**
   * HTML5数据存储
   */
  private void saveData(WebSettings mWebSettings) {
    //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
    mWebSettings.setDomStorageEnabled(true);
    mWebSettings.setDatabaseEnabled(true);
    mWebSettings.setAppCacheEnabled(true);
    String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
    mWebSettings.setAppCachePath(appCachePath);
  }

  WebViewClient webViewClient = new WebViewClient() {

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     */
    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      Log.d(TAG, "shouldOverrideUrlLoading: " + url);
      return super.shouldOverrideUrlLoading(view, url);
    }

    @Override public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      toolbar.setTitle(view.getTitle());
    }
  };

  WebChromeClient webChromeClient = new WebChromeClient() {
    @Override public void onProgressChanged(WebView view, int progress) {
      Log.d(TAG, "progress: " + progress);
      if (progress < 100) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(progress);
      } else if (progress == 100) {
        progressBar.setVisibility(View.INVISIBLE);
      }
    }

    @Override public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
      toolbar.setTitle(title);
    }

    //=========HTML5定位==========================================================
    //需要先加入权限
    //<uses-permission android:name="android.permission.INTERNET"/>
    //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    @Override public void onReceivedIcon(WebView view, Bitmap icon) {
      super.onReceivedIcon(view, icon);
    }

    @Override public void onGeolocationPermissionsHidePrompt() {
      super.onGeolocationPermissionsHidePrompt();
    }

    @Override public void onGeolocationPermissionsShowPrompt(final String origin,
        final GeolocationPermissions.Callback callback) {
      callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
      super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
    //=========HTML5定位==========================================================

    //=========多窗口的问题==========================================================
    @Override public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture,
        Message resultMsg) {
      WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
      transport.setWebView(view);
      resultMsg.sendToTarget();
      return true;
    }
    //=========多窗口的问题==========================================================
  };

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
      webView.goBack();
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    if (webView != null) {
      webView.clearHistory();
      ((ViewGroup) webView.getParent()).removeView(webView);
      webView.loadUrl("about:blank");
      webView.stopLoading();
      webView.setWebChromeClient(null);
      webView.setWebViewClient(null);
      webView.destroy();
      webView = null;
    }
  }
}
