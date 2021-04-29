package com.example.navigation.ui.settings;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.navigation.R;
import com.example.navigation.ui.home.HomeViewModel;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        ListPreference listPreference;
        private SettingsViewModel settingsViewModel;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            listPreference = (ListPreference) findPreference("chooseBackground");
            if(listPreference.getValue()==null) {
                // to ensure we don't get a null value
                // set first value by default
                listPreference.setValueIndex(0);
            }
            listPreference.setSummary(listPreference.getValue().toString());
            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(newValue.toString());
                    return true;
                }
            });
        }
        public void saveBackground(View v) {
            settingsViewModel.saveBackground(listPreference.getValue().toString());
        }
    }
}