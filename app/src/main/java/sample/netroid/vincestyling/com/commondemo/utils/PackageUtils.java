package sample.netroid.vincestyling.com.commondemo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import sample.netroid.vincestyling.com.commondemo.exception.ExceptionHandlerFactory;

/**
 * Created by haipingguo on 16-12-12.
 */
public class PackageUtils {
    public static boolean isApkInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA);
            return true;
        } catch (Exception e) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(e);
            return false;
        }
    }
}
