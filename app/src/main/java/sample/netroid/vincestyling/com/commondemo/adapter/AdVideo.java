package sample.netroid.vincestyling.com.commondemo.adapter;

import android.content.Context;
import android.text.TextUtils;

import java.util.Map;

import sample.netroid.vincestyling.com.commondemo.VideoManager;
import sample.netroid.vincestyling.com.commondemo.listener.VideoListener;
import sample.netroid.vincestyling.com.commondemo.mediation.CustomEventVideo;
import sample.netroid.vincestyling.com.commondemo.model.PingStartReward;
import sample.netroid.vincestyling.com.commondemo.utils.ErrorCode;

/**
 * Created by haipingguo on 16-12-9.
 */
public class AdVideo extends CustomEventVideo implements VideoListener {
    private static final String PUBLISHER_ID = "publisher_id";
    private static final String SLOT_ID = "slot_id";

    private CustomEventVideoListener mCustomlistener;
    private VideoManager mVideoManager;

    @Override
    public void loadVideo(Context context, Map<String, String> serverExtras, CustomEventVideoListener listener) {
        mCustomlistener = listener;
        if (context == null) {
            mCustomlistener.onVideoAdFailed(ErrorCode.ERROR_INVALID_CONTEXT);
            return;
        }
        String publisherId = serverExtras.get(PUBLISHER_ID);
        String soltId = serverExtras.get(SLOT_ID);
        if (TextUtils.isEmpty(publisherId) || TextUtils.isEmpty(soltId)) {
            mCustomlistener.onVideoAdFailed(ErrorCode.ERROR_INVALID_CONTEXT);
            return;
        }
        mVideoManager = VideoManager.getInstance(context, publisherId, soltId);
        mVideoManager.setVideoListener(this);
        mVideoManager.loadAdVideo();
    }

    @Override
    public void show() {
        if (mVideoManager != null) {
            mVideoManager.show();
        }
    }

    @Override
    public void destroy() {
        if (mVideoManager != null) {
            mVideoManager.destroy();
        }
    }

    @Override
    public boolean isVideoAvailable() {
        return false;
    }

    @Override
    public void onAdClosed() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoLoaded() {

    }

    @Override
    public void onVideoRewarded(PingStartReward reward) {

    }

    @Override
    public void onAdError(String error) {

    }

    @Override
    public void onAdClicked() {

    }
}
