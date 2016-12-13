package sample.netroid.vincestyling.com.commondemo.config;

import android.content.Context;

import sample.netroid.vincestyling.com.commondemo.utils.PreferencesUtils;

/**
 * Created by haipingguo on 16-12-8.
 */
public class PingStartConfig {
    public static final String NBT_ADS_SDK_PS_SHOWED_APP_PKGS_SEPARATOR = "#";
    private static final String NBT_VIDEO_CONFIG = "_video_config";
    private static final String NBT_ADS_SDK_VIDEO_LAST_SHOW_AD = "video_last_show_ad";
    //video config配置不同，再存入
    public static void setVideoConfigResult(Context context, String slotId, String result) {
        if (!result.equals(getVideoConfigResult(context, slotId))) {
            PreferencesUtils.setPreferenceString(context, slotId + NBT_VIDEO_CONFIG, result);
        }
    }

    public static String getVideoConfigResult(Context context, String slotId) {
        return PreferencesUtils.getPreferenceString(context, slotId + NBT_VIDEO_CONFIG, "");
    }

    public static void setVideoShowListAd(Context context, String slotId, String result) {
        if (!result.equals(getVideoConfigResult(context, slotId))) {
            PreferencesUtils.setPreferenceString(context, NBT_ADS_SDK_VIDEO_LAST_SHOW_AD, result);
        }
    }

    public static String getVideoShowListAd(Context context) {
        return PreferencesUtils.getPreferenceString(context, NBT_ADS_SDK_VIDEO_LAST_SHOW_AD, "");
    }

    public static void emptyListAds(Context context) {
         PreferencesUtils.setPreferenceString(context, NBT_ADS_SDK_VIDEO_LAST_SHOW_AD, "");
    }
}
