package sample.netroid.vincestyling.com.commondemo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by haipingguo on 16-12-12.
 */
public class BaseNativeAd implements Parcelable {
    protected String mTitle;
    protected String mDescription;
    protected String mIconUrl;
    protected String mCoverImageUrl;
    protected String mClickUrl;
    protected String mCallToAction;
    protected String mNetworkName;

    @Override
    public int describeContents() {
        return 0;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmIconUrl() {
        return mIconUrl;
    }

    public void setmIconUrl(String mIconUrl) {
        this.mIconUrl = mIconUrl;
    }

    public String getmCoverImageUrl() {
        return mCoverImageUrl;
    }

    public void setmCoverImageUrl(String mCoverImageUrl) {
        this.mCoverImageUrl = mCoverImageUrl;
    }

    public String getmClickUrl() {
        return mClickUrl;
    }

    public void setmClickUrl(String mClickUrl) {
        this.mClickUrl = mClickUrl;
    }

    public String getmCallToAction() {
        if (TextUtils.isEmpty(mCallToAction)) {
            mCallToAction = "GO";
        }
        return mCallToAction;
    }

    public void setmCallToAction(String mCallToAction) {
        this.mCallToAction = mCallToAction;
    }

    public String getmNetworkName() {
        return mNetworkName;
    }

    public void setmNetworkName(String mNetworkName) {
        this.mNetworkName = mNetworkName;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mIconUrl);
        dest.writeString(this.mCoverImageUrl);
        dest.writeString(this.mClickUrl);
        dest.writeString(this.mCallToAction);
        dest.writeString(this.mNetworkName);
    }

    public BaseNativeAd() {
    }

    protected BaseNativeAd(Parcel in) {
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mIconUrl = in.readString();
        this.mCoverImageUrl = in.readString();
        this.mClickUrl = in.readString();
        this.mCallToAction = in.readString();
        this.mNetworkName = in.readString();
    }

}
