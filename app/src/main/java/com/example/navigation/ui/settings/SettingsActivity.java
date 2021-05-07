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

            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    saveBackground(newValue);
                    return true;
                }
            });
        }
        public void saveBackground(Object newValue) {
            settingsViewModel.saveBackground(newValue.toString());
        }
    }
}