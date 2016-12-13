package sample.netroid.vincestyling.com.commondemo.network;


/**
 * Created by Messi on 16-8-18.
 */
public class UrlBuilder {

    private static final int SDK_VERSION_CODE = 141;

   /* *//**
     * 广告配置请求url
     *//*
    public static String buildAdConfigUrl(Context context, String url, String publishId, String slotId) {
        StringBuilder builder = new StringBuilder();
        ParamUtils.addParam(builder, "publisher_id", publishId);
        ParamUtils.addParam(builder, "slot_id", slotId);
        ParamUtils.addParam(builder, "app_id", publishId);
        ParamUtils.addParam(builder, "platform", "android");
        ParamUtils.addParam(builder, "aid", DeviceUtils.getAndroidId(context));
        ParamUtils.addParam(builder, "gaid", AdConfig.getAdvertisingId(context));
        ParamUtils.addParam(builder, "language", LocaleUtils.getLocaleLanguage(context));
        ParamUtils.addParam(builder, "version_code", AdConstants.VERSION_CODE);
        ParamUtils.addParam(builder, "osv", DeviceUtils.getOSVersion());
        ParamUtils.addParam(builder, "app_name", context.getPackageName());
        ParamUtils.addParam(builder, "app_version_code", String.valueOf(PackageUtils.getVersionCode(context)));

        return url + builder.toString();
    }

    *//**
     * 视频配置请求
     *//*
    public static String buildVideoConfigUrl(Context context, String url, String slotId) {
        StringBuilder builder = new StringBuilder();
        builder.append(slotId);
        return url + builder.toString();
    }

    *//**
     * 优化服务请求数据url
     *//*
    public static String buildOptUrl(Context context, String publisherId, String slotId, int adType) {
        StringBuilder builder = new StringBuilder();
        try {
            ParamUtils.addParam(builder, "sid", String.valueOf(slotId));
            ParamUtils.addParam(builder, "aid", String.valueOf(publisherId));
            ParamUtils.addParam(builder, "campaign", LocaleUtils.getCountryISOCode(context));
            ParamUtils.addParam(builder, "version_code", String.valueOf(SDK_VERSION_CODE));
            ParamUtils.addParam(builder, "ads_id", AdConfig.getAdvertisingId(context));
            ParamUtils.addParam(builder, "api_level", String.valueOf(Build.VERSION.SDK_INT));
            // device build
            ParamUtils.addParam(builder, "os", "1");
            ParamUtils.addParam(builder, "osv", Build.VERSION.RELEASE);
            ParamUtils.addParam(builder, "dmf", NetWorkUtils.encode(Build.MANUFACTURER));
            ParamUtils.addParam(builder, "dml", NetWorkUtils.encode(Build.MODEL));
            ParamUtils.addParam(builder, "dpd", NetWorkUtils.encode(Build.PRODUCT));
            // orientation
            int orientation = UiUtils.getOrientation(context);
            ParamUtils.addParam(builder, "so", String.valueOf(orientation));
            // density
            float density = UiUtils.getScreenDensity(context);
            ParamUtils.addParam(builder, "ds", String.valueOf(density));

            // mcc, mnc
            String networkOperator = TelephonyUtils.getNetworkOperator(context);
            if (!TextUtils.isEmpty(networkOperator)) {
                int mncPortionLength = Math.min(3, networkOperator.length());
                String mcc = networkOperator.substring(0, mncPortionLength);
                ParamUtils.addParam(builder, "mcc", NetWorkUtils.encode(mcc));
                String mnc = networkOperator.substring(mncPortionLength);
                ParamUtils.addParam(builder, "mnc", NetWorkUtils.encode(mnc));
            }

            // mnc
            ParamUtils.addParam(builder, "udid", NetWorkUtils.encode(DeviceUtils.getDeviceUUID(context)));
            // iso country code
            String isoCode = LocaleUtils.getCountryISOCode(context);
            ParamUtils.addParam(builder, "icc", isoCode);
            // carrier name
            String carrierName = TelephonyUtils.getNetworkOperatorName(context);
            ParamUtils.addParam(builder, "cn", carrierName);
            // network type
            int networkType = ConnectivityManager.TYPE_DUMMY;
            if (context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                NetworkInfo activeNetworkInfo = NetWorkUtils.getActiveNetworkInfo(context);
                networkType = activeNetworkInfo != null ? activeNetworkInfo
                        .getType() : ConnectivityManager.TYPE_DUMMY;
            }
            ParamUtils.addParam(builder, "nt", NetWorkUtils.encode(String.valueOf(networkType)));
            ParamUtils.addParam(builder, "adnum", "20");
            ParamUtils.addParam(builder, "adType", String.valueOf(adType));
            ParamUtils.addParam(builder, "app_name", context.getPackageName());
            ParamUtils.addParam(builder, "f", "1");
        } catch (Exception e) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(e);
        }
        return AdConstants.URL_PRELOAD + builder.toString();
    }

    *//**
     * sdk 配置请求url
     *//*
    public static String buildOptConfigUrl(Context context, String publisherId) {
        String isoCode = LocaleUtils.getCountryISOCode(context);
        return "http://api.pingstart.com:17209/v1/sdk_config"
                + "?campaign="
                + isoCode
                + "&publisherId="
                + publisherId;
    }

    *//**
     * ps 广告请求url
     *//*
    @SuppressWarnings("deprecation")
    public static String buildPsUrl(Context context, String publisherId, String slotId, boolean isVideo) {
        StringBuilder builder = new StringBuilder();
        try {
            ParamUtils.addParam(builder, "publisherid", publisherId);
            ParamUtils.addParam(builder, "slotid", slotId);
            ParamUtils.addParam(builder, "lang", LocaleUtils.getLocaleLanguage(context));//语言
            ParamUtils.addParam(builder, "timestamp", Long.toString(System.currentTimeMillis()));//时间戳
            ParamUtils.addParam(builder, "platform", "android");//操作系统平台
            ParamUtils.addParam(builder, "osv", Build.VERSION.RELEASE);//操作系统版本
            ParamUtils.addParam(builder, "dpi", Float.toString(context.getResources().getDisplayMetrics().densityDpi));//手机密度
            ParamUtils.addParam(builder, "tzone", TimeUtils.getCurrentTimeZone());//时区
            ParamUtils.addParam(builder, "aid", DeviceUtils.getAndroidId(context));//android id
            ParamUtils.addParam(builder, "gaid", AdConfig.getAdvertisingId(context));//Google Advertising Id
            int networkType = ConnectivityManager.TYPE_DUMMY;
            if (context.checkCallingOrSelfPermission(ACCESS_NETWORK_STATE) == PERMISSION_GRANTED) {
                NetworkInfo activeNetworkInfo = NetWorkUtils.getActiveNetworkInfo(context);
                networkType = activeNetworkInfo != null ? activeNetworkInfo
                        .getType() : ConnectivityManager.TYPE_DUMMY;
            }
            // orientation
            int orientation = UiUtils.getOrientation(context);
            ParamUtils.addParam(builder, "orientation", String.valueOf(orientation));
            // density
            float density = UiUtils.getScreenDensity(context);
            ParamUtils.addParam(builder, "density", String.valueOf(density));

            ParamUtils.addParam(builder, "nt", String.valueOf(networkType));//网络类型
            ParamUtils.addParam(builder, "model", NetWorkUtils.encode(Build.MODEL));//设备型号
            ParamUtils.addParam(builder, "brand", NetWorkUtils.encode(Build.BRAND));//设备品牌
            if (PackageUtils.isApkInstalled(context, AppConstants.NBT_ADS_SDK_GP_PKG)) {
                ParamUtils.addParam(builder, "gp", "1");
            } else {
                ParamUtils.addParam(builder, "gp", "0");
            }
            ParamUtils.addParam(builder, "root", String.valueOf(DeviceUtils.isRoot()));
            ParamUtils.addParam(builder, "versioncode", AdConstants.VERSION_CODE);
            ParamUtils.addParam(builder, "app_versioncode", String.valueOf(PackageUtils.getVersionCode(context)));
            ParamUtils.addParam(builder, "app_name", context.getPackageName());
            String networkOperator = TelephonyUtils.getNetworkOperator(context);
            if (!TextUtils.isEmpty(networkOperator)) {
                int mncPortionLength = Math.min(3, networkOperator.length());
                String mcc = networkOperator
                        .substring(0, mncPortionLength);
                ParamUtils.addParam(builder, "mcc", NetWorkUtils.encode(mcc));
                String mnc = networkOperator
                        .substring(mncPortionLength);
                ParamUtils.addParam(builder, "mnc", NetWorkUtils.encode(mnc));
            }
            if (isVideo) {
                ParamUtils.addParam(builder, "ad_type", "video");
            }
        } catch (Exception e) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(e);
        }
        return AdConstants.URL_PINGSTART_AD + builder.toString();
    }

    *//**
     * 搜索广告请求url
     *//*
    public static String buildSearchUrl(Context context, String publisherId, String slotId) {
        StringBuilder builder = new StringBuilder();
        ParamUtils.addParam(builder, "publisherid", publisherId);
        ParamUtils.addParam(builder, "slotid", slotId);
        ParamUtils.addParam(builder, "lang", LocaleUtils.getLocaleLanguage(context));//语言
        ParamUtils.addParam(builder, "timestamp", Long.toString(System.currentTimeMillis()));//时间戳
        ParamUtils.addParam(builder, "platform", "android");//操作系统平台
        ParamUtils.addParam(builder, "osv", Build.VERSION.RELEASE);//操作系统版本
        ParamUtils.addParam(builder, "dpi", String.valueOf(UiUtils.getScreenDensity(context)));//手机密度
        ParamUtils.addParam(builder, "tzone", TimeUtils.getCurrentTimeZone());//时区
        ParamUtils.addParam(builder, "aid", DeviceUtils.getAndroidId(context));//android id
        ParamUtils.addParam(builder, "gaid", AdConfig.getAdvertisingId(context));//Google Advertising Id
        int networkType = ConnectivityManager.TYPE_DUMMY;
        if (context.checkCallingOrSelfPermission(ACCESS_NETWORK_STATE) == PERMISSION_GRANTED) {
            NetworkInfo activeNetworkInfo = NetWorkUtils.getActiveNetworkInfo(context);
            networkType = activeNetworkInfo != null ? activeNetworkInfo
                    .getType() : ConnectivityManager.TYPE_DUMMY;
        }
        ParamUtils.addParam(builder, "nt", NetWorkUtils.encode(String.valueOf(networkType)));//网络类型
        ParamUtils.addParam(builder, "model", NetWorkUtils.encode(Build.MODEL));//设备型号
        ParamUtils.addParam(builder, "brand", NetWorkUtils.encode(Build.BRAND));//设备品牌
        if (PackageUtils.isApkInstalled(context, AppConstants.NBT_ADS_SDK_GP_PKG)) {
            ParamUtils.addParam(builder, "gp", "1");
        } else {
            ParamUtils.addParam(builder, "gp", "0");
        }
        ParamUtils.addParam(builder, "root", String.valueOf(DeviceUtils.isRoot()));
        ParamUtils.addParam(builder, "versioncode", AdConstants.VERSION_CODE);
        ParamUtils.addParam(builder, "app_versioncode", String.valueOf(PackageUtils.getVersionCode(context)));
        ParamUtils.addParam(builder, "app_name", context.getPackageName());
        // mcc, mnc
        String networkOperator = TelephonyUtils.getNetworkOperator(context);
        if (!TextUtils.isEmpty(networkOperator)) {
            int mncPortionLength = Math.min(3, networkOperator.length());
            String mcc = networkOperator.substring(0, mncPortionLength);
            ParamUtils.addParam(builder, "mcc", NetWorkUtils.encode(mcc));
            String mnc = networkOperator.substring(mncPortionLength);
            ParamUtils.addParam(builder, "mnc", NetWorkUtils.encode(mnc));
        }
        return AdConstants.URL_HOT_WORD + builder.toString();
    }*/
}
