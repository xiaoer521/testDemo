package sample.netroid.vincestyling.com.commondemo.utils;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sample.netroid.vincestyling.com.commondemo.config.PingStartConfig;
import sample.netroid.vincestyling.com.commondemo.constants.AdConstants;
import sample.netroid.vincestyling.com.commondemo.exception.ExceptionHandlerFactory;
import sample.netroid.vincestyling.com.commondemo.model.MediationConfig;
import sample.netroid.vincestyling.com.commondemo.network.request.StringRequest;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;
import sample.netroid.vincestyling.com.commondemo.network.utils.VolleyError;

/**
 * Created by haipingguo on 16-12-8.
 */
public class AdConfigHelper {
    private static AdConfigHelper mInstance;
    private OnConfigChangedListener mListener;
    private OnVideoConfigChangedListener mVideoListener;
    private Map<String, Map<String, String>> mAdExtras = new HashMap<>();

    public static AdConfigHelper getInstance(){
        if(mInstance==null){
            mInstance=new AdConfigHelper();
        }
        return mInstance;
    }

    public void loadVideoConfig(final Context context, final String slotId) {
        StringRequest stringRequest = new StringRequest(AdConstants.TEST_URL_VIDEO_CONFIG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!TextUtils.isEmpty(response)) {
                    PingStartConfig.setVideoConfigResult(context, slotId, response);
                    if(mVideoListener!=null){
                        mVideoListener.onVideoConfigChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(mVideoListener!=null){
                    mVideoListener.onVideoConfigError(error.getMessage());
                }
            }
        });
        stringRequest.setTag("data");
        VolleyUtil.getDateRequestQueue().add(stringRequest);
    }



    public void loadAdConfig() {
        String url = AdConstants.TEST_URL_MEDIATION_CONFIG;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!TextUtils.isEmpty(response)) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArray=jsonObject.getJSONArray("ad");
                        sortAdConfig(jsonArray);
                    } catch (JSONException e) {
                        if (mListener != null) {
                            mListener.onConfigError("request ad config error " + e.getMessage());
                        }
                        ExceptionHandlerFactory.createExceptionHandler().handleException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("data");
        VolleyUtil.getDateRequestQueue().add(stringRequest);
    }

    private void sortAdConfig(JSONArray jsonArray) {
        ArrayList<MediationConfig> configs = new ArrayList<MediationConfig>();
        ArrayList<String> adList = new ArrayList<>();
        ArrayList<Integer> weights = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            MediationConfig config = new MediationConfig(jsonObject);
            int weight = jsonObject.optInt("weight");
            JSONObject extra = jsonObject.optJSONObject("placement_id");
            String customCls = jsonObject.optString("custom_cls");
            mAdExtras.put(weight+customCls,config.parseExtraData(extra));
            configs.add(config);
        }

        Collections.sort(configs, new Comparator<MediationConfig>() {
            @Override
            public int compare(MediationConfig arg0, MediationConfig arg1) {
                return arg1.getWeight() - (arg0.getWeight());
            }
        });
        for (MediationConfig config : configs) {
            //weight + customCls====8com.pingstart.adsdk.adapter.AdVideo
            //8
            adList.add(config.getPlatform() + "#" + config.getCustomCls());
            weights.add(config.getWeight());
        }
        if (mListener != null) {
            mListener.onConfigChanged(adList, weights, mAdExtras);
        }
    }


    public void setOnConfigChangedListener(OnConfigChangedListener listener) {
        this.mListener = listener;
    }

    public void setOnVideoConfigChangedListener(OnVideoConfigChangedListener listener) {
        this.mVideoListener = listener;
    }

    public interface OnVideoConfigChangedListener {
        void onVideoConfigChanged();

        void onVideoConfigError(String error);
    }
    public interface OnConfigChangedListener {
        void onConfigChanged(List<String> clsNames, List<Integer> weights, Map<String, Map<String, String>> extras);

        void onConfigError(String error);
    }
}
