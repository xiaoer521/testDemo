package sample.netroid.vincestyling.com.commondemo.config;

import android.content.Context;
import android.text.TextUtils;

import sample.netroid.vincestyling.com.commondemo.utils.PreferencesUtils;

/**
 * Created by haipingguo on 16-12-8.
 */
public class AdConfig {
    private static final String NBT_ADS_SDK_PREFS_KEY_USER_AGENT = "key_user_agent";

    private static String sUserAgent = null;

    public static String getUserAgent(Context context) {
        if (TextUtils.isEmpty(sUserAgent)) {
            sUserAgent = PreferencesUtils.getPreferenceString(context, NBT_ADS_SDK_PREFS_KEY_USER_AGENT, "");
        }
        return sUserAgent;
    }
}
