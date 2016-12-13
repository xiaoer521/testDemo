package sample.netroid.vincestyling.com.commondemo.network.request;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sample.netroid.vincestyling.com.commondemo.config.PingStartConfig;
import sample.netroid.vincestyling.com.commondemo.exception.ExceptionHandlerFactory;
import sample.netroid.vincestyling.com.commondemo.model.VideoAd;
import sample.netroid.vincestyling.com.commondemo.network.toolbox.HttpHeaderParser;
import sample.netroid.vincestyling.com.commondemo.network.utils.NetworkResponse;
import sample.netroid.vincestyling.com.commondemo.network.utils.Request;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;
import sample.netroid.vincestyling.com.commondemo.utils.PackageUtils;

public class VideoRequest extends Request<VideoAd> {
    private static final int NBT_ADS_SDK_ADS_POSITION_INVALID = -1;

    private final Response.Listener<VideoAd> mListener;
    private Context mContext;
    private String mSlotId;
    private int mCurrentPosition;

    public VideoRequest(Context context, String slotId, int method, String url, Response.Listener<VideoAd> listener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mContext = context;
        mSlotId = slotId;
    }

    public VideoRequest(Context context, String slotId, String url, Response.Listener<VideoAd> listener, Response.ErrorListener errorListener) {
        this(context, slotId, Method.GET, url, listener, errorListener);
    }

    @Override
    protected Response<VideoAd> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }
        List<VideoAd> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(parsed);
            JSONArray array = object.optJSONArray(GZipRequest.NBT_ADS_SDK_JSON_FILED_APPS);
            if (array != null) {
                int length = array.length();
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        VideoAd ad = new VideoAd(array.optJSONObject(i));
                        if (!PackageUtils.isApkInstalled(mContext, ad.getmPkgName())) {
                            list.add(ad);
                        }
                       /* FileUtils.saveObject(mContext, ad, mSlotId, i);
                        ad.downloadVideo(mContext);*/
                    }
                }
            }
        } catch (JSONException e) {
            ExceptionHandlerFactory.createExceptionHandler().handleException(e);
        }
        if (!list.isEmpty()) {
            sureCurrentPosition(list);
        }

        return Response.success(list.get(mCurrentPosition));
    }

    private void sureCurrentPosition(List<VideoAd> list) {
        int adsCurrentShowPosition = getAdsCurrentShowPosition(list);
        if(adsCurrentShowPosition==NBT_ADS_SDK_ADS_POSITION_INVALID){
            PingStartConfig.emptyListAds(mContext);
            mCurrentPosition=0;
        }
    }

    private int getAdsCurrentShowPosition( List<VideoAd> pkgs) {
        String videoShowListAd = PingStartConfig.getVideoShowListAd(mContext);
        List<String> strLists = Arrays.asList(videoShowListAd.split(PingStartConfig.NBT_ADS_SDK_PS_SHOWED_APP_PKGS_SEPARATOR));
        for(int i=0;i<pkgs.size();i++){
            if(!strLists.contains(pkgs.get(i).getmPkgName())){
                mCurrentPosition=i;
            }
        }
        return NBT_ADS_SDK_ADS_POSITION_INVALID;
    }

    @Override
    protected void deliverResponse(VideoAd response) {
        // TODO 自动生成的方法存根
        this.mListener.onResponse(response);
    }
}
