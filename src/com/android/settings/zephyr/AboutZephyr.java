/*
 * Copyright (C) 2016 crDroid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.zephyr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.MetricsProto.MetricsEvent;

public class AboutZephyr extends SettingsPreferenceFragment {

    public static final String TAG = "About";

    private static final String KEY_ZEPHYR_SHARE = "share";

    Preference mSourceUrl;
    Preference mGoogleUrl;
    preference mTelegramUrl;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.about_zephyr);

        mSourceUrl = findPreference("zephyr_source");
	mTelegramUrl = findPreference("zephyr_telegram");
        mGoogleUrl = findPreference("zephyr_google_plus");
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.DONT_TRACK_ME_BRO;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == mSourceUrl) {
            launchUrl("https://github.com/Zephyr-OS");
        } else if (preference == mTelegramUrl) {
            launchUrl("https://t.me/joinchat/AAAAAD6aVfdMm5pmko_R8g");
        } else if (preference == mGoogleUrl) {
            launchUrl("https://plus.google.com/u/0/communities/101103673497024297251");
        } else if (preference.getKey().equals(KEY_ZEPHYR_SHARE)) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, String.format(
                getActivity().getString(R.string.share_message), Build.MODEL));
        startActivity(Intent.createChooser(intent, getActivity().getString(R.string.share_chooser_title)));
        }
        return super.onPreferenceTreeClick(preference);
    }

    private void launchUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
        getActivity().startActivity(intent);
    }
}
