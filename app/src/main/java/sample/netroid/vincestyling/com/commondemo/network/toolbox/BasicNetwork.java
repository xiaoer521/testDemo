package sample.netroid.vincestyling.com.commondemo.network.toolbox;


import android.os.SystemClock;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import sample.netroid.vincestyling.com.commondemo.network.utils.Network;
import sample.netroid.vincestyling.com.commondemo.network.utils.NetworkResponse;
import sample.netroid.vincestyling.com.commondemo.network.utils.Request;
import sample.netroid.vincestyling.com.commondemo.network.utils.RetryPolicy;
import sample.netroid.vincestyling.com.commondemo.network.utils.VolleyError;

public class BasicNetwork implements Network {

    private static int DEFAULT_POOL_SIZE = 4096;

    protected final HttpStack mHttpStack;

    protected final ByteArrayPool mPool;

    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
    }

    public BasicNetwork(HttpStack httpStack, ByteArrayPool pool) {
        mHttpStack = httpStack;
        mPool = pool;
    }

    private static void attemptRetryOnException(Request<?> request,
                                                VolleyError exception) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        try {
            retryPolicy.retry(exception);
        } catch (VolleyError e) {
            throw e;
        }

    }

    protected static Map<String, String> convertHeaders(Header[] headers) {
        Map<String, String> result = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headers.length; i++) {
            result.put(headers[i].getName(), headers[i].getValue());
        }
        return result;
    }

    @Override
    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        long requestStart = SystemClock.elapsedRealtime();
        while (true) {
            HttpResponse httpResponse = null;
            byte[] responseContents = null;
            Map<String, String> responseHeaders = Collections.emptyMap();
            try {
                Map<String, String> headers = new HashMap<String, String>();
                httpResponse = mHttpStack.performRequest(request, headers);
                StatusLine statusLine = httpResponse.getStatusLine();
                int statusCode = statusLine.getStatusCode();

                responseHeaders = convertHeaders(httpResponse.getAllHeaders());
                if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
                    return new NetworkResponse(HttpStatus.SC_NOT_MODIFIED, null,
                            responseHeaders, true,
                            SystemClock.elapsedRealtime() - requestStart);
                }

                if (httpResponse.getEntity() != null) {
                    responseContents = entityToBytes(httpResponse.getEntity());
                } else {
                    responseContents = new byte[0];
                }

                if (statusCode < 200 || statusCode > 299) {
                    throw new IOException();
                }
                return new NetworkResponse(statusCode, responseContents, responseHeaders, false,
                        SystemClock.elapsedRealtime() - requestStart);
            } catch (SocketTimeoutException e) {
                attemptRetryOnException(request, new VolleyError());
            } catch (ConnectTimeoutException e) {
                attemptRetryOnException(request, new VolleyError());
            } catch (MalformedURLException e) {
                throw new RuntimeException("Bad URL " + request.getUrl(), e);
            } catch (IOException e) {
                int statusCode;
                if (httpResponse != null) {
                    statusCode = httpResponse.getStatusLine().getStatusCode();
                } else {
                    throw new VolleyError(e);
                }
                NetworkResponse networkResponse;
                if (responseContents != null) {
                    networkResponse = new NetworkResponse(statusCode, responseContents,
                            responseHeaders, false, SystemClock.elapsedRealtime() - requestStart);
                    if (statusCode == HttpStatus.SC_UNAUTHORIZED ||
                            statusCode == HttpStatus.SC_FORBIDDEN) {
                        attemptRetryOnException(
                                request, new VolleyError(networkResponse));
                    } else if (statusCode >= 400 && statusCode <= 499) {
                        // Don't retry other client errors.
                        throw new VolleyError(networkResponse);
                    } else if (statusCode >= 500 && statusCode <= 599) {
                        if (request.shouldRetryServerErrors()) {
                            attemptRetryOnException(
                                    request, new VolleyError(networkResponse));
                        } else {
                            throw new VolleyError(networkResponse);
                        }
                    } else {
                        throw new VolleyError(networkResponse);
                    }
                } else {
                    attemptRetryOnException(request, new VolleyError());
                }
            }
        }
    }

    private byte[] entityToBytes(HttpEntity entity) throws IOException, VolleyError {
        PoolingByteArrayOutputStream bytes =
                new PoolingByteArrayOutputStream(mPool, (int) entity.getContentLength());
        byte[] buffer = null;
        try {
            InputStream in = entity.getContent();
            if (in == null) {
                throw new VolleyError();
            }
            buffer = mPool.getBuf(1024);
            int count;
            while ((count = in.read(buffer)) != -1) {
                bytes.write(buffer, 0, count);
            }
            return bytes.toByteArray();
        } finally {
            try {
                entity.consumeContent();
            } catch (IOException e) {

            }
            mPool.returnBuf(buffer);
            bytes.close();
        }
    }
}
