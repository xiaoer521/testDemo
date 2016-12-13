package sample.netroid.vincestyling.com.commondemo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by haipingguo on 16-12-8.
 */
public class MediationConfig implements Parcelable{
    private int weight;
    private String platform;
    private String customCls;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCustomCls() {
        return customCls;
    }

    public void setCustomCls(String customCls) {
        this.customCls = customCls;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.weight);
        dest.writeString(this.platform);
        dest.writeString(this.customCls);
    }

    public MediationConfig() {
    }
    public MediationConfig(JSONObject object) {
        if (object != null) {
            this.weight = object.optInt("weight");
            this.platform = object.optString("platform");
            this.customCls = object.optString("custom_cls");
        }
    }

    protected MediationConfig(Parcel in) {
        this.weight = in.readInt();
        this.platform = in.readString();
        this.customCls = in.readString();
    }

    public static final Creator<MediationConfig> CREATOR = new Creator<MediationConfig>() {
        @Override
        public MediationConfig createFromParcel(Parcel source) {
            return new MediationConfig(source);
        }

        @Override
        public MediationConfig[] newArray(int size) {
            return new MediationConfig[size];
        }
    };

    public Map<String, String> parseExtraData(JSONObject extra) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = extra.keys();
        while (iterator.hasNext()) {
            keys.add(iterator.next());
        }
        Map<String, String> map = new HashMap<>();
        for (String key : keys) {
            if (!TextUtils.isEmpty(key)) {
                map.put(key, extra.optString(key));
            }
        }
        return map;
    }
}
