package com.plusend.diycode.view.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.plusend.diycode.R;

public class SettingsFragment extends PreferenceFragment {
  
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);
  }
}
