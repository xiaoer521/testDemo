package sample.netroid.vincestyling.com.commondemo.listener;

import sample.netroid.vincestyling.com.commondemo.model.PingStartReward;

/**
 * Created by haipingguo on 16-12-8.
 */
public interface VideoListener extends BaseListener {
    void onAdClosed();

    void onVideoStarted();

    void onVideoLoaded();

    void onVideoRewarded(PingStartReward reward);
}
