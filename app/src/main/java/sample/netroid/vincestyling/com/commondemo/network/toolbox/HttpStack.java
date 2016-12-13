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

package sample.netroid.vincestyling.com.commondemo.network.toolbox;


import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

import sample.netroid.vincestyling.com.commondemo.network.utils.Request;
import sample.netroid.vincestyling.com.commondemo.network.utils.VolleyError;

public interface HttpStack {

    HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
            throws IOException, VolleyError;

}
