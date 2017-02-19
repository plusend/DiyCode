package com.plusend.diycode.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.plusend.diycode.R;
import com.plusend.diycode.util.ToastUtil;
import com.plusend.diycode.view.activity.MainActivity;
import permissions.dispatcher.NeedsPermission;

public class SettingsFragment extends PreferenceFragment {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);

    addCheckUpdate();
    addFeedback();
  }

  @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) public void addCheckUpdate() {
    Preference myPref = findPreference(getResources().getString(R.string.pref_key_check_update));
    myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference preference) {
        //PgyUpdateManager.register(getActivity(), getString(R.string.file_provider));
        PgyUpdateManager.register(getActivity(), getString(R.string.file_provider),
            new UpdateManagerListener() {
              @Override public void onUpdateAvailable(final String result) {
                // 将新版本信息封装到AppBean中
                final AppBean appBean = getAppBeanFromString(result);
                new AlertDialog.Builder(getActivity()).setTitle("更新")
                    .setMessage(appBean.getReleaseNote())
                    .setNegativeButton("取消", null)
                    .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                      @Override public void onClick(DialogInterface dialog, int which) {
                        startDownloadTask(getActivity(), appBean.getDownloadURL());
                      }
                    })
                    .show();
              }

              @Override public void onNoUpdateAvailable() {
                ToastUtil.showText(getActivity(), "已经是最新版本了");
              }
            });
        return true;
      }
    });
  }

  @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) private void addFeedback() {
    Preference myPref = findPreference(getResources().getString(R.string.pref_key_feedback));
    myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference preference) {
        PgyFeedback.getInstance().showDialog(getActivity());
        return true;
      }
    });
  }
}
