package sample.netroid.vincestyling.com.commondemo.mediation;

import android.content.Context;

import java.util.Map;

import sample.netroid.vincestyling.com.commondemo.model.PingStartReward;

/**
 * Created by haipingguo on 16-12-8.
 */
public abstract class CustomEventVideo {
    public abstract void loadVideo(Context context, Map<String, String> serverExtras,
                                   CustomEventVideo.CustomEventVideoListener listener);

    public abstract void show();

    public abstract void destroy();

    public abstract boolean isVideoAvailable();

    public interface CustomEventVideoListener {
        void onVideoAdLoaded();

        void onVideoAdOpened();

        void onVideoStarted();

        void onVideoAdClosed();

        void onVideoRewarded(PingStartReward reward);

        void onVideoAdClicked();

        void onVideoAdFailed(String error);
    }
}
