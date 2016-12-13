package sample.netroid.vincestyling.com.commondemo.model;

import android.support.annotation.NonNull;

/**
 * Created by haipingguo on 16-12-8.
 */
public class PingStartReward {
    public static final String NO_REWARD_LABEL = "";
    public static final int NO_REWARD_AMOUNT = -123;
    public static final int DEFAULT_REWARD_AMOUNT = 0;

    private final boolean mSuccess;
    private final String mLabel;
    private final int mAmount;

    private PingStartReward(boolean success, @NonNull String label, int amount) {
        mSuccess = success;
        mLabel = label;
        mAmount = amount;
    }

}
