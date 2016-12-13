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

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;

import java.util.concurrent.BlockingQueue;


public class NetworkDispatcher extends Thread {
    /**
     * The queue of requests to service.
     */
    private final BlockingQueue<Request<?>> mQueue;
    /**
     * The network interface for processing requests.
     */
    private final Network mNetwork;
    /**
     * For posting responses and errors.
     */
    private final ResponseDelivery mDelivery;
    /**
     * Used for telling us to die.
     */
    private volatile boolean mQuit = false;


    public NetworkDispatcher(BlockingQueue<Request<?>> queue,
                             Network network, ResponseDelivery delivery) {
        mQueue = queue;
        mNetwork = network;
        mDelivery = delivery;
    }

    /**
     * Forces this dispatcher to quit immediately.  If any requests are still in
     * the queue, they are not guaranteed to be processed.
     */
    public void quit() {
        mQuit = true;
        interrupt();


    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void addTrafficStatsTag(Request<?> request) {
        TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (!mQuit) {
            Request<?> request;
            try {
                // Take a request from the queue.
                request = mQueue.take();
            } catch (InterruptedException e) {
                // We may have been interrupted because it was time to quit.
                if (mQuit) {
                    return;
                }
                continue;
            }

            try {
                // If the request was cancelled already, do not perform the
                // network request.
                if (request.isCanceled()) {
                    request.finish();
                    continue;
                }

                addTrafficStatsTag(request);
                // Perform the network request.
                NetworkResponse networkResponse = mNetwork.performRequest(request);
                // If the server returned 304 AND we delivered a response already,
                // we're done -- don't deliver a second identical response.
                if (networkResponse.notModified && request.hasHadResponseDelivered()) {
                    request.finish();
                    continue;
                }

                // Parse the response here on the worker thread.
                Response<?> response = request.parseNetworkResponse(networkResponse);
                // Post the response back.
                request.markDelivered();
                mDelivery.postResponse(request, response);
            } catch (VolleyError volleyError) {
                parseAndDeliverNetworkError(request, volleyError);
            } catch (Exception e) {
                VolleyError volleyError = new VolleyError(e);
                mDelivery.postError(request, volleyError);
            }
        }
    }

    private void parseAndDeliverNetworkError(Request<?> request, VolleyError error) {
        error = request.parseNetworkError(error);
        mDelivery.postError(request, error);
    }
}
