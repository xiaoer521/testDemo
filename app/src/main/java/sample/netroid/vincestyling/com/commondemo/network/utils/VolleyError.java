package sample.netroid.vincestyling.com.commondemo.network.utils;

/**
 * 异常样式类封装的错误
 */
public class VolleyError extends Exception {
    public final NetworkResponse networkResponse;

    public VolleyError() {
        networkResponse = null;
    }

    public VolleyError(NetworkResponse response) {
        networkResponse = response;
    }

    public VolleyError(String exceptionMessage) {
        super(exceptionMessage);
        networkResponse = null;
    }

    public VolleyError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        networkResponse = null;
    }

    public VolleyError(Throwable cause) {
        super(cause);
        networkResponse = null;
    }
}
