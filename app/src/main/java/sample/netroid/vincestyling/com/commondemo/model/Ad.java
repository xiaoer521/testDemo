package sample.netroid.vincestyling.com.commondemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haipingguo on 16-12-12.
 */
public class Ad extends BaseNativeAd implements Parcelable{
    private String mPkgName;
    private String[] mUrlImpression;
    private String[] mUrlTrack;
    private int mNeedJump;
    private int mNeedRedirect;

    public String getmPkgName() {
        return mPkgName;
    }

    public void setmPkgName(String mPkgName) {
        this.mPkgName = mPkgName;
    }

    public String[] getmUrlImpression() {
        return mUrlImpression;
    }

    public void setmUrlImpression(String[] mUrlImpression) {
        this.mUrlImpression = mUrlImpression;
    }

    public String[] getmUrlTrack() {
        return mUrlTrack;
    }

    public void setmUrlTrack(String[] mUrlTrack) {
        this.mUrlTrack = mUrlTrack;
    }

    public int getmNeedJump() {
        return mNeedJump;
    }

    public void setmNeedJump(int mNeedJump) {
        this.mNeedJump = mNeedJump;
    }

    public int getmNeedRedirect() {
        return mNeedRedirect;
    }

    public void setmNeedRedirect(int mNeedRedirect) {
        this.mNeedRedirect = mNeedRedirect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mPkgName);
        dest.writeStringArray(this.mUrlImpression);
        dest.writeStringArray(this.mUrlTrack);
        dest.writeInt(this.mNeedJump);
        dest.writeInt(this.mNeedRedirect);
    }

    public Ad() {
    }

    protected Ad(Parcel in) {
        super(in);
        this.mPkgName = in.readString();
        this.mUrlImpression = in.createStringArray();
        this.mUrlTrack = in.createStringArray();
        this.mNeedJump = in.readInt();
        this.mNeedRedirect = in.readInt();
    }

}
