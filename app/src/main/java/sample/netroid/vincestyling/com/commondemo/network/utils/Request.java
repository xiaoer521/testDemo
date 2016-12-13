/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.netroid.vincestyling.com.commondemo.network.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;


public abstract class Request<T> implements Comparable<Request<T>> {

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private final int mMethod;
    private final String mUrl;
    private final int mDefaultTrafficStatsTag;
    private final Response.ErrorListener mErrorListener;
    private Integer mSequence;
    private RequestQueue mRequestQueue;
    private boolean mCanceled = false;
    private boolean mResponseDelivered = false;
    private boolean mShouldRetryServerErrors = false;
    private RetryPolicy mRetryPolicy;
    private Object mTag;

    @Deprecated
    public Request(String url, Response.ErrorListener listener) {
        this(Method.DEPRECATED_GET_OR_POST, url, listener);
    }


    public Request(int method, String url, Response.ErrorListener listener) {
        mMethod = method;
        mUrl = url;
        mErrorListener = listener;
        setRetryPolicy(new DefaultRetryPolicy());
        mDefaultTrafficStatsTag = findDefaultTrafficStatsTag(url);
    }

    private static int findDefaultTrafficStatsTag(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                String host = uri.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }

    public int getMethod() {
        return mMethod;
    }

    public Object getTag() {
        return mTag;
    }

    public Request<?> setTag(Object tag) {
        mTag = tag;
        return this;
    }

    public int getTrafficStatsTag() {
        return mDefaultTrafficStatsTag;
    }

    void finish() {
        if (mRequestQueue != null) {
            mRequestQueue.finish(this);
        }
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
        return this;
    }

    public final Request<?> setSequence(int sequence) {
        mSequence = sequence;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public void cancel() {
        mCanceled = true;
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    public Map<String, String> getHeaders() throws VolleyError {
        return Collections.emptyMap();
    }

    @Deprecated
    protected Map<String, String> getPostParams() throws VolleyError {
        return getParams();
    }

    @Deprecated
    protected String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Deprecated
    public byte[] getPostBody() throws VolleyError {
        Map<String, String> postParams = getPostParams();
        if (postParams != null && postParams.size() > 0) {
            return encodeParameters(postParams, getPostParamsEncoding());
        }
        return null;
    }

    protected Map<String, String> getParams() throws VolleyError {
        return null;
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public byte[] getBody() throws VolleyError {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    public final boolean shouldRetryServerErrors() {
        return mShouldRetryServerErrors;
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public final int getTimeoutMs() {
        return mRetryPolicy.getCurrentTimeout();
    }

    public RetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        mRetryPolicy = retryPolicy;
        return this;
    }

    public void markDelivered() {
        mResponseDelivered = true;
    }

    public boolean hasHadResponseDelivered() {
        return mResponseDelivered;
    }

    abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    abstract protected void deliverResponse(T response);

    public void deliverError(VolleyError error) {
        if (mErrorListener != null) {
            mErrorListener.onErrorResponse(error);
        }
    }

    @Override
    public int compareTo(Request<T> other) {
        Priority left = this.getPriority();
        Priority right = other.getPriority();
        return left == right ?
                this.mSequence - other.mSequence :
                right.ordinal() - left.ordinal();
    }

    @Override
    public String toString() {
        String trafficStatsTag = "0x" + Integer.toHexString(getTrafficStatsTag());
        return (mCanceled ? "[X] " : "[ ] ") + getUrl() + " " + trafficStatsTag + " "
                + getPriority() + " " + mSequence;
    }


    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
}
