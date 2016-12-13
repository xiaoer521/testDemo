package sample.netroid.vincestyling.com.commondemo.mediation;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import sample.netroid.vincestyling.com.commondemo.listener.VideoListener;
import sample.netroid.vincestyling.com.commondemo.utils.AdConfigHelper;
import sample.netroid.vincestyling.com.commondemo.utils.ErrorCode;

/**
 * Created by haipingguo on 16-12-8.
 */
public class PingStartVideo implements AdConfigHelper.OnConfigChangedListener {
    private static PingStartVideo sInstance;
    private VideoListener mListener;
    private Context mContext;
    private String mPublisherId;
    private String mSlotId;
    private CustomEventVideoAdapter mVideoAdapter;

    private List<String> mAdList;
    private List<Integer> mWeightsList;
    private Map<String, Map<String, String>> mExtras;

    public PingStartVideo(Context context,String publisherId, String slotId){
        mContext=context;
        mPublisherId = publisherId;
        mSlotId = slotId;
    }

    public static void  initializeAndLoad(Context context,String publisherId, String slotId) {
      if(context==null){
          throw new IllegalArgumentException(ErrorCode.ERROR_INVALID_CONTEXT);
      }
        if(sInstance==null||!TextUtils.equals(sInstance.mSlotId,slotId)){
            sInstance=new PingStartVideo(context,publisherId,slotId);
        }
        //拉取广告配置
        sInstance.loadConfig();
    }
    public static void setVideoListener(VideoListener listener) {
        sInstance.mListener = listener;
    }

    private void loadConfig() {
        AdConfigHelper helper=AdConfigHelper.getInstance();
        helper.setOnConfigChangedListener(this);
        helper.loadAdConfig();
    }


    @Override
    public void onConfigChanged(List<String> clsNames, List<Integer> weights, Map<String, Map<String, String>> extras) {
        if (clsNames != null && !clsNames.isEmpty()) {
            mAdList = clsNames;
            mWeightsList = weights;
            mExtras = extras;
            startLoad();
        }
    }

    private void startLoad() {
        if (mVideoAdapter == null) {
            mVideoAdapter = new CustomEventVideoAdapter(mContext, mAdList, mWeightsList, mExtras, mListener);
        }
        mVideoAdapter.loadVideo();
    }

    @Override
    public void onConfigError(String error) {

    }
}
