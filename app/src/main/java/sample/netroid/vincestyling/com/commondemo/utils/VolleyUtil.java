package sample.netroid.vincestyling.com.commondemo.utils;

import sample.netroid.vincestyling.com.commondemo.network.toolbox.Volley;
import sample.netroid.vincestyling.com.commondemo.network.utils.RequestQueue;

/**
 * Created by haipingguo on 16-12-8.
 */
public class VolleyUtil {
    private static RequestQueue sDataRequestQueue;
    /**
     * 获取volley requestQueue
     * @return
     */
    public static RequestQueue getDateRequestQueue() {
        if (sDataRequestQueue == null) {
            sDataRequestQueue = Volley.newRequestQueue();
        }
        return sDataRequestQueue;
    }

}
