package sample.netroid.vincestyling.com.commondemo.mediation;

import android.content.Context;

import java.util.List;
import java.util.Map;

import sample.netroid.vincestyling.com.commondemo.listener.VideoListener;
import sample.netroid.vincestyling.com.commondemo.model.PingStartReward;

/**
 * Created by haipingguo on 16-12-8.
 */
public class CustomEventVideoAdapter implements  CustomEventVideo.CustomEventVideoListener{
    private String mCurrentPlatform;
    private int mCurrentIndex = 0;

    private Context mContext;
    private VideoListener mlistener;
    private List<String> mClassList;
    private List<Integer> mWeightsList;
    private Map<String, Map<String, String>> mServerExtras;
    private CustomEventVideo mEventVideo;

    CustomEventVideoAdapter(Context context, List<String> list,
                            List<Integer> weights,
                            Map<String, Map<String, String>> extras,
                            VideoListener listener) {
        this.mContext = context;
        this.mlistener = listener;
        //1 PingStart#com.pingstart.adsdk.adapter.AdVideo
        this.mClassList = list;
        //1 8
        this.mWeightsList = weights;
        //8com.pingstart.adsdk.adapter.AdVideo  {"slot_id":"1000223","publisher_id":"5079"}
        this.mServerExtras = extras;


    }

    void loadVideo() {
        try {
            String platformCls = mClassList.get(mCurrentIndex);
            String[] tmp = platformCls.split("#");
            String clsName = tmp[1];
            mCurrentPlatform = tmp[0];
            int weight = mWeightsList.get(mCurrentIndex);
            mEventVideo = CustomEventFactory.createVideoInstance(clsName);
            mEventVideo.loadVideo(mContext, mServerExtras.get(weight + clsName), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVideoAdLoaded() {

    }

    @Override
    public void onVideoAdOpened() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoAdClosed() {

    }

    @Override
    public void onVideoRewarded(PingStartReward reward) {

    }

    @Override
    public void onVideoAdClicked() {

    }

    @Override
    public void onVideoAdFailed(String error) {

    }
}
