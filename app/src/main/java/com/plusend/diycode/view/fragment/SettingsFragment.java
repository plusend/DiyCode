package com.plusend.diycode.view.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.plusend.diycode.R;
import com.plusend.diycode.view.service.MyIntentService;

public class SettingsFragment extends PreferenceFragment {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);

    addCheckUpdate();
  }

  private void addCheckUpdate() {
    Preference myPref = findPreference(getResources().getString(R.string.pref_key_check_update));
    myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      public boolean onPreferenceClick(Preference preference) {
        MyIntentService.startActionUpdate(getActivity());
        return true;
      }
    });
  }
}
