package sample.netroid.vincestyling.com.commondemo.network.request;


import android.content.Context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sample.netroid.vincestyling.com.commondemo.config.AdConfig;
import sample.netroid.vincestyling.com.commondemo.network.utils.NetworkResponse;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;
import sample.netroid.vincestyling.com.commondemo.network.utils.VolleyError;

public class GZipRequest extends StringRequest {
    public static final String NBT_ADS_SDK_REQUEST_TAG_DATA = "data";
    public static final String NBT_ADS_SDK_JSON_FILED_APPS = "apps";

    private Context mContext;

    public GZipRequest(Context context, int method, String url,
                       Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mContext = context;
    }

    public GZipRequest(Context context, String url, Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        mContext = context;
    }

    // parse the gzip response using a GZIPInputStream
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String output = "";
        if (response.data.length > 10000) {
            // setShouldCache(false);
        }
        output = new String(response.data);
        /*return Response.success(output,
                HttpHeaderParser.parseCacheHeaders(response));*/
        return Response.success(output);
    }

    @Override
    public Map<String, String> getHeaders() throws VolleyError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        String userAgent = AdConfig.getUserAgent(mContext);
        headers.put("User-Agent", userAgent);
        return headers;
    }
}