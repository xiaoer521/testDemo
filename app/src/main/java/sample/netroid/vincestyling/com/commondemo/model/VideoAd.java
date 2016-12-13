package sample.netroid.vincestyling.com.commondemo.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONObject;

import sample.netroid.vincestyling.com.commondemo.network.request.FileDownloadRequest;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;
import sample.netroid.vincestyling.com.commondemo.network.utils.VolleyError;

/**
 * Created by haipingguo on 16-12-8.
 */
public class VideoAd extends Ad implements Parcelable {
    private String mSourceUrl;
    private String mLastUpdateTime;
    private long mCreateTime;

    public String getmSourceUrl() {
        return mSourceUrl;
    }

    public void setmSourceUrl(String mSourceUrl) {
        this.mSourceUrl = mSourceUrl;
    }

    public String getmLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setmLastUpdateTime(String mLastUpdateTime) {
        this.mLastUpdateTime = mLastUpdateTime;
    }

    public long getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(long mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mSourceUrl);
        dest.writeString(this.mLastUpdateTime);
        dest.writeLong(this.mCreateTime);
    }

    public VideoAd(JSONObject json) {
        mSourceUrl = json.optString("source_url");
        mLastUpdateTime = json.optString("last_update_time");
        mCreateTime = System.currentTimeMillis();
    }

    protected VideoAd(Parcel in) {
        super(in);
        this.mSourceUrl = in.readString();
        this.mLastUpdateTime = in.readString();
        this.mCreateTime = in.readLong();
    }

    public static final Creator<VideoAd> CREATOR = new Creator<VideoAd>() {
        @Override
        public VideoAd createFromParcel(Parcel source) {
            return new VideoAd(source);
        }

        @Override
        public VideoAd[] newArray(int size) {
            return new VideoAd[size];
        }
    };

    public void downloadVideo(Context context) {
        if (!TextUtils.isEmpty(mSourceUrl)) {
            /*String saveFilePath = FileUtils.getSaveFilePath(context,mSourceUrl);*/
            FileDownloadRequest fileDownLoadRequest=new FileDownloadRequest(mSourceUrl, new Response.Listener<byte[]>() {
                @Override
                public void onResponse(byte[] response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }
}
