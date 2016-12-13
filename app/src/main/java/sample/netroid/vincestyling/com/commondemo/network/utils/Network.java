package sample.netroid.vincestyling.com.commondemo.network.utils;


public interface Network {
    /**
     * 执行指定的请求
     */
    NetworkResponse performRequest(Request<?> request) throws VolleyError;
}
