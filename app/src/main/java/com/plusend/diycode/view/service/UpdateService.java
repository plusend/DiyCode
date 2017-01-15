package com.plusend.diycode.view.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.plusend.diycode.R;
import com.plusend.diycode.util.Constant;
import com.plusend.diycode.util.PackageUtil;
import com.plusend.diycode.util.ToastUtil;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateService extends Service {
  private static final String TAG = "MyIntentService";

  public static final String ACTION_UPDATE = "com.plusend.diycode.update";

  private CompleteReceiver mCompleteReceiver;

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null) {
      final String action = intent.getAction();
      if (ACTION_UPDATE.equals(action)) {
        handleActionUpdate();
      }
    }
    return super.onStartCommand(intent, flags, startId);
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

        if (PackageUtil.getVersionCode(UpdateService.this) < versionCode) {
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
          ToastUtil.showText(UpdateService.this,
              getResources().getString(R.string.version_already));
        }
      }

      @Override public void onFail(Exception exception) {
        Log.i(TAG, "check fir.im fail! " + "\n" + exception.getMessage());
        ToastUtil.showText(UpdateService.this, getResources().getString(R.string.version_failed));
      }

      @Override public void onStart() {
        ToastUtil.showText(UpdateService.this, getResources().getString(R.string.version_start));
      }

      @Override public void onFinish() {
        //ToastUtil.showText(UpdateService.this, getResources().getString(R.string.version_finish));
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
      installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
      installIntent.setDataAndType(downloadManager.getUriForDownloadedFile(completeDownloadId),
          downloadManager.getMimeTypeForDownloadedFile(completeDownloadId));
      startActivity(installIntent);
      stopSelf();
    }
  }

  @Override public void onDestroy() {
    if (mCompleteReceiver != null) {
      unregisterReceiver(mCompleteReceiver);
    }
    super.onDestroy();
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }
}
