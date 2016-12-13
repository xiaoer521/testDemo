package sample.netroid.vincestyling.com.commondemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sample.netroid.vincestyling.com.commondemo.R;
import sample.netroid.vincestyling.com.commondemo.listener.VideoListener;
import sample.netroid.vincestyling.com.commondemo.mediation.PingStartVideo;
import sample.netroid.vincestyling.com.commondemo.model.PingStartReward;

public class AdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        loadAdVideo();
    }

    private void loadAdVideo() {
        PingStartVideo.initializeAndLoad(getApplicationContext(),"5079", "1000223");
        PingStartVideo.setVideoListener(new VideoListener() {
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
        });
    }
}
