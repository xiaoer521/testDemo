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

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个线程池的请求调度队列调度
 */

public class RequestQueue {

    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;
    private final Set<Request<?>> mCurrentRequests = new HashSet<Request<?>>();
    private final PriorityBlockingQueue<Request<?>> mNetworkQueue =
            new PriorityBlockingQueue<Request<?>>();
    private final Network mNetwork;
    private final ResponseDelivery mDelivery;
    //在这里会设置序列号，保证每个请求都是按顺序被处理的。
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private NetworkDispatcher[] mDispatchers;
    private List<RequestFinishedListener> mFinishedListeners = new ArrayList<>();
    private Object mBeforRequestTag;

    public RequestQueue(Network network) {
        mNetwork = network; //网络
        mDispatchers = new NetworkDispatcher[DEFAULT_NETWORK_THREAD_POOL_SIZE];//线程池
        mDelivery = new ExecutorDelivery(new Handler(Looper.getMainLooper())); //派送Response的实现

    }

    public void start() {
        // 保证所有正在运行的Dispatcher（也就是线程）都停止
        stop();
        // 根据线程池的大小，创建相对应的NetworkDispatcher（线程），并启动所有的线程。
        for (int i = 0; i < mDispatchers.length; i++) {
            NetworkDispatcher networkDispatcher = new NetworkDispatcher(mNetworkQueue, mNetwork, mDelivery);
            mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    /**
     * 停止缓存线程跟所有的网络线程
     */
    public void stop() {

        for (int i = 0; i < mDispatchers.length; i++) {
            if (mDispatchers[i] != null) {
                mDispatchers[i].quit();
            }
        }
    }

    public int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    public void cancelAll(RequestFilter filter) {
        synchronized (mCurrentRequests) {
            for (Request<?> request : mCurrentRequests) {
                if (filter.apply(request)) {
                    request.cancel();
                }
            }
        }
    }

    public void cancelAll(final Object tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Cannot cancelAll with a null tag");
        }
        cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request.getTag() == tag;
            }
        });
    }

    public <T> Request<T> add(Request<T> request) {
        cancleBeforeDownloadZip(request);
        // 将请求的队列设置为当前队列，并将请求添加到mCurrentRequests中，表明是正在处理中的，而在这里，我们可以看到利用synchronized来同步
        request.setRequestQueue(this);
        synchronized (mCurrentRequests) {
            mCurrentRequests.add(request);
        }
        request.setSequence(getSequenceNumber());
        //将其添加到mNetworkQueue中，直接去网络中获取数据
        mNetworkQueue.add(request);
        return request;
    }

    private <T> void cancleBeforeDownloadZip(Request<T> request) {
        if (request.getUrl().endsWith(".zip")) {
            if (mBeforRequestTag != null) {
                cancelAll(mBeforRequestTag);
                start();
            }
            mBeforRequestTag = request.getTag();
        }
    }

    <T> void finish(Request<T> request) {
        synchronized (mCurrentRequests) {
            mCurrentRequests.remove(request);
        }
        synchronized (mFinishedListeners) {
            for (RequestFinishedListener<T> listener : mFinishedListeners) {
                listener.onRequestFinished(request);
            }
        }
    }

    public interface RequestFinishedListener<T> {

        void onRequestFinished(Request<T> request);
    }

    public interface RequestFilter {
        boolean apply(Request<?> request);
    }
}
