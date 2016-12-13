package sample.netroid.vincestyling.com.commondemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import sample.netroid.vincestyling.com.commondemo.view.CustomVideoView;

public class VideoViewActivity extends AppCompatActivity {
    private CustomVideoView mVideoView;
    private SeekBar mSeekBar;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        mVideoView= (CustomVideoView) findViewById(R.id.videoView);
        mSeekBar= (SeekBar) findViewById(R.id.seekbar);

        Uri mUri=Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.vedio_guide);
        mVideoView.setVideoURI(mUri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mSeekBar.setMax(mVideoView.getDuration());
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //int progress = seekBar.getProgress();
                mSeekBar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //显示 Loading 图
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //隐藏 Loading 图
                        break;
                }

                return false;
            }
        });

        new Thread(){
            @Override
            public void run() {
                super.run();
                isPlaying=true;
                while (isPlaying){
                    mSeekBar.setProgress(mVideoView.getCurrentPosition());
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mVideoView.start();
    }
}
