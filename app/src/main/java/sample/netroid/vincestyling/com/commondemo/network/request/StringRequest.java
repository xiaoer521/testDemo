package sample.netroid.vincestyling.com.commondemo.network.request;


import java.io.UnsupportedEncodingException;

import sample.netroid.vincestyling.com.commondemo.network.toolbox.HttpHeaderParser;
import sample.netroid.vincestyling.com.commondemo.network.utils.NetworkResponse;
import sample.netroid.vincestyling.com.commondemo.network.utils.Request;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;

public class StringRequest extends Request<String> {
    private final Response.Listener<String> mListener;

    public StringRequest(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed);
    }
}
