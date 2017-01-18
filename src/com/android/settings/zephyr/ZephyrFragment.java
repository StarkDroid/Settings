package com.android.settings.zephyr;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.Utils;
import com.android.settings.cyanogenmod.SystemSettingSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import com.android.internal.logging.MetricsProto.MetricsEvent;

public class ZephyrFragment extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

	private static final String KEY_MOD_BUILD_DATE = "build_date";
	private static final String KEY_ZEPHYR_VERSION = "zephyr_version";
	private static final String KEY_DEVICE_MAINTAINER = "device_maintainer";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.zephyr_fragment);

	setValueSummary(KEY_MOD_BUILD_DATE, "ro.build.date");
	setValueSummary(KEY_ZEPHYR_VERSION, "ro.zephyr.version");
	findPreference(KEY_ZEPHYR_VERSION).setEnabled(true);
	setMaintainerSummary(KEY_DEVICE_MAINTAINER, "ro.zephyr.maintainer");
    }

    private void setMaintainerSummary(String preference, String property) {
        try {
            String maintainers = SystemProperties.get(property,
                    getResources().getString(R.string.device_info_default));
            findPreference(preference).setSummary(maintainers);
            if (maintainers.contains(",")) {
                findPreference(preference).setTitle(
                        getResources().getString(R.string.device_maintainers));
            }
        } catch (RuntimeException e) {
            // No recovery
        }
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.DONT_TRACK_ME_BRO;
    }
}
