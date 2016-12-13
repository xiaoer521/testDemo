package sample.netroid.vincestyling.com.commondemo;

import android.content.Context;
import android.text.TextUtils;

import sample.netroid.vincestyling.com.commondemo.constants.AdConstants;
import sample.netroid.vincestyling.com.commondemo.listener.VideoListener;
import sample.netroid.vincestyling.com.commondemo.model.VideoAd;
import sample.netroid.vincestyling.com.commondemo.network.request.VideoRequest;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;
import sample.netroid.vincestyling.com.commondemo.network.utils.VolleyError;
import sample.netroid.vincestyling.com.commondemo.utils.AdConfigHelper;
import sample.netroid.vincestyling.com.commondemo.utils.ErrorCode;
import sample.netroid.vincestyling.com.commondemo.utils.VolleyUtil;

/**
 * Created by haipingguo on 16-12-12.
 */
public class VideoManager implements AdConfigHelper.OnVideoConfigChangedListener {
    private static VideoManager mInstance;
    private VideoListener mVideoListener;

    private Context mContext;
    private String mSoltId;
    private String mPublisherId;

    public VideoManager(Context context, String publisher, String soltId) {
        mContext = context;
        mSoltId = soltId;
        mPublisherId = publisher;
    }

    public static VideoManager getInstance(Context context, String soltId, String publisherId) {
        if (context == null) {
            throw new IllegalArgumentException(ErrorCode.ERROR_INVALID_CONTEXT);
        }
        if (mInstance == null) {
            mInstance = new VideoManager(context, publisherId,soltId);
        } else {
            if (!TextUtils.equals(mInstance.mSoltId, soltId)) {
                mInstance = new VideoManager(context,publisherId, soltId);
            }

        }
        return mInstance;
    }

    public void setVideoListener(VideoListener videoListener) {
        this.mVideoListener = videoListener;
    }

    public void loadAdVideo(){
        AdConfigHelper helper = AdConfigHelper.getInstance();
        helper.setOnVideoConfigChangedListener(this);
        helper.loadVideoConfig(mContext, mSoltId);
    }

    public void show(){}

    public void destroy(){}

    @Override
    public void onVideoConfigChanged() {
        startLoadData();
    }

    private void startLoadData() {
        String url = AdConstants.TEST_URL_VIDEO_REQUST;
        VideoRequest videoRequest=new VideoRequest(mContext, mSoltId, url, new Response.Listener<VideoAd>() {
            @Override
            public void onResponse(VideoAd videoAd) {
                videoAd.downloadVideo(mContext);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        videoRequest.setTag(url);
        VolleyUtil.getDateRequestQueue().add(videoRequest);
    }

    @Override
    public void onVideoConfigError(String error) {

    }
}
