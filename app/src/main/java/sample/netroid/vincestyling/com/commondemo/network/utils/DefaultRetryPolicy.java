package sample.netroid.vincestyling.com.commondemo.network.utils;

/**
 * 请求的默认重试策略
 */
public class DefaultRetryPolicy implements RetryPolicy {
    /**
     * 在millisecondscy缺省套接字超时
     */
    public static final int DEFAULT_TIMEOUT_MS = 10000;
    /**
     * 重试的默认值
     */
    public static final int DEFAULT_MAX_RETRIES = 0;
    /**
     * 默认的退避乘数
     */
    public static final float DEFAULT_BACKOFF_MULT = 1f;
    /**
     * 尝试的最大次数
     */
    private final int mMaxNumRetries;
    /**
     * 对于政策的退避乘数
     */
    private final float mBackoffMultiplier;
    /**
     * 当前超时毫秒
     */
    private int mCurrentTimeoutMs;
    /**
     * 当前重试计数
     */
    private int mCurrentRetryCount;

    /**
     * 使用默认的超时重试政策构建了一个新的。
     */
    public DefaultRetryPolicy() {
        this(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT);
    }

    public DefaultRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier) {
        mCurrentTimeoutMs = initialTimeoutMs;
        mMaxNumRetries = maxNumRetries;
        mBackoffMultiplier = backoffMultiplier;
    }

    @Override
    public int getCurrentTimeout() {
        return mCurrentTimeoutMs;
    }


    @Override
    public int getCurrentRetryCount() {
        return mCurrentRetryCount;
    }


    /**
     * Prepares for the next retry by applying a backoff to the timeout.
     *
     * @param error The error code of the last attempt.
     */
    @Override
    public void retry(VolleyError error) throws VolleyError {
        mCurrentRetryCount++;
        mCurrentTimeoutMs += (mCurrentTimeoutMs * mBackoffMultiplier);
        if (!hasAttemptRemaining()) {
            throw error;
        }
    }

    protected boolean hasAttemptRemaining() {
        return mCurrentRetryCount <= mMaxNumRetries;
    }
}
