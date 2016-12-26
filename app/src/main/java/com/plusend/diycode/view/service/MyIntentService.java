package com.plusend.diycode.view.service;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;
import com.plusend.diycode.R;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.PackageUtil;
import com.plusend.diycode.util.ToastUtil;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class MyIntentService extends IntentService {
  private static final String TAG = "MyIntentService";

  private static final String ACTION_UPDATE = "com.plusend.diycode.update";

  private CompleteReceiver mCompleteReceiver;

  public MyIntentService() {
    super("MyIntentService");
  }

  public static void startActionUpdate(Context context) {
    Intent intent = new Intent(context, MyIntentService.class);
    intent.setAction(ACTION_UPDATE);
    context.startService(intent);
  }

  @Override protected void onHandleIntent(Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();
      if (ACTION_UPDATE.equals(action)) {
        handleActionUpdate();
      }
    }
  }

  private void handleActionUpdate() {
    checkUpdate();
  }

  //检测更新
  private void checkUpdate() {
    FIR.checkForUpdateInFIR(Constant.FIR_API_TOKEN, new VersionCheckCallback() {
      @Override public void onSuccess(String versionJson) {
        Log.d(TAG, "check from fir.im success! " + "\n" + versionJson);
        int versionCode = 0;
        String updateUrl = null;
        try {
          JSONObject version = new JSONObject(versionJson);
          versionCode = version.optInt("version");
          updateUrl = version.optString("direct_install_url");
        } catch (JSONException e) {
          e.printStackTrace();
        }

        if (PackageUtil.getVersionCode(MyIntentService.this) < versionCode) {
          // 下载新版本
          DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
          DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateUrl));
          request.setDestinationInExternalPublicDir("Download", "DiyCode.apk");
          // Wi-Fi下载
          request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
          downloadManager.enqueue(request);

          // 注册下载完成监听器
          mCompleteReceiver = new CompleteReceiver();
          /** register download success broadcast **/
          registerReceiver(mCompleteReceiver,
              new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } else {
          ToastUtil.showText(MyIntentService.this,
              getResources().getString(R.string.version_already));
        }
      }

      @Override public void onFail(Exception exception) {
        Log.i(TAG, "check fir.im fail! " + "\n" + exception.getMessage());
      }

      @Override public void onStart() {
        ToastUtil.showText(MyIntentService.this, getResources().getString(R.string.version_start));
      }

      @Override public void onFinish() {
        ToastUtil.showText(MyIntentService.this, getResources().getString(R.string.version_finish));
      }
    });
  }

  // 下载完成广播接受者
  class CompleteReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
      // 获取下载Id
      long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
      // 安装新版本
      Intent installIntent = new Intent(Intent.ACTION_VIEW);
      DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
      installIntent.setDataAndType(downloadManager.getUriForDownloadedFile(completeDownloadId),
          "application/vnd.android.package-archive");
      startActivity(installIntent);
    }
  }

  @Override public void onDestroy() {
    if (mCompleteReceiver != null) {
      unregisterReceiver(mCompleteReceiver);
    }
    super.onDestroy();
  }
}
