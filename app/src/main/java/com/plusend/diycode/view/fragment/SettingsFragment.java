package com.plusend.diycode.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.plusend.diycode.R;
import com.plusend.diycode.util.ToastUtil;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions public class SettingsFragment extends PreferenceFragment {

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        addSignOut();
        addCheckUpdate();
        addFeedback();
    }

    private void addSignOut() {
        Preference myPref = findPreference(getResources().getString(R.string.pref_key_sign_out));
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getResources().getString(R.string.logout_intent_action));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getActivity().startActivity(intent);
                return true;
            }
        });
    }

    private void addCheckUpdate() {
        Preference myPref =
            findPreference(getResources().getString(R.string.pref_key_check_update));
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SettingsFragmentPermissionsDispatcher.checkUpdateWithPermissionCheck(
                    SettingsFragment.this);
                return true;
            }
        });
    }

    private void addFeedback() {
        Preference myPref = findPreference(getResources().getString(R.string.pref_key_feedback));
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SettingsFragmentPermissionsDispatcher.feedbackWithPermissionCheck(
                    SettingsFragment.this);
                return true;
            }
        });
    }

    @NeedsPermission({
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO
    }) void checkUpdate() {
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
    }

    @NeedsPermission({
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO
    }) void feedback() {
        PgyFeedback.getInstance().showDialog(getActivity());
    }

    @OnShowRationale({
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO
    }) void showRationaleForWriteExternalStorage(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity()).setMessage("需要使用存储空间/电话/麦克风权限")
            .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    request.proceed();
                }
            })
            .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    request.cancel();
                }
            })
            .show();
    }

    @OnPermissionDenied({
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO
    }) void showDeniedForWriteExteralStorage() {
        ToastUtil.showText(getActivity(), "您已经拒绝了使用存储空间/电话/麦克风权限中的一项或全部");
    }

    @OnNeverAskAgain({
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO
    }) void showNeverAskForWriteExteralStorage() {
        new AlertDialog.Builder(getActivity()).setPositiveButton("好的",
            new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    startInstalledAppDetailsActivity(getActivity());
                }
            })
            .setNegativeButton("取消", null)
            .setCancelable(false)
            .setMessage("您已经禁止了存储空间/电话/麦克风权限中的一项或全部,是否现在去开启")
            .show();
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        SettingsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
            grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
