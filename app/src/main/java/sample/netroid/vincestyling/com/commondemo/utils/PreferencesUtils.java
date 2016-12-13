package sample.netroid.vincestyling.com.commondemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import sample.netroid.vincestyling.com.commondemo.exception.ExceptionHandlerFactory;

/**
 * Created by haipingguo on 16-12-8.
 */
public class PreferencesUtils {
    public static final String NBT_ADS_SDK_PREFS_NAME = "com.pingstart.adsdk.preference";
    private static final Method sApplyMethod = findApplyMethod();

    /**
     * 反射查找apply的方法
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Method findApplyMethod() {
        try {
            Class clz = SharedPreferences.Editor.class;
            return clz.getMethod("apply");
        } catch (NoSuchMethodException e) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(e);
        }

        return null;
    }

    /*获取内部存储量*/
    public static String getPreferenceString(Context context, String name, String def) {
        try {
            return getSharedPreferences(context).getString(name, def);
        } catch (Exception ex) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(ex);
        }
        return def;
    }
    public static SharedPreferences getSharedPreferences(Context context) {
        try {
            return context.getSharedPreferences(NBT_ADS_SDK_PREFS_NAME, Context.MODE_PRIVATE);
        } catch (Exception ex) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(ex);
        }
        return null;
    }

    public static void setPreferenceString(Context context, String name, String value) {
        try {
            apply(getSettingsEditor(context).putString(name, value));
        } catch (NullPointerException ex) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(ex);
        }
    }

    public static SharedPreferences.Editor getSettingsEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     *
     * @param editor
     */
    public static void apply(SharedPreferences.Editor editor) {
        try {
            if (sApplyMethod != null) {
                sApplyMethod.invoke(editor);
                return;
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(ex);
        }
        editor.commit();
    }
}
