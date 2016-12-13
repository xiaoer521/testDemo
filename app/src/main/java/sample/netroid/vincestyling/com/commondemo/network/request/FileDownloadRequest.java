package sample.netroid.vincestyling.com.commondemo.network.request;


import sample.netroid.vincestyling.com.commondemo.network.utils.NetworkResponse;
import sample.netroid.vincestyling.com.commondemo.network.utils.Request;
import sample.netroid.vincestyling.com.commondemo.network.utils.Response;

/**
 * Created by haipingguo on 16-11-29.
 */
public class FileDownloadRequest extends Request<byte[]> {

    private Response.Listener<byte[]> mListener;

    public FileDownloadRequest(int method, String url, Response.Listener<byte[]> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public FileDownloadRequest(String url, Response.Listener<byte[]> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse paramNetworkResponse) {
        return Response.success(paramNetworkResponse.data);
    }

}