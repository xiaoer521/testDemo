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


import org.apache.http.protocol.HTTP;

import java.util.Map;

/**
 * Utility methods for parsing HTTP headers.
 */
public class HttpHeaderParser {

    /**
     * Retrieve a charset from headers
     *
     * @param headers        An {@link java.util.Map} of headers
     * @param defaultCharset Charset to return if none can be found
     * @return Returns the charset specified in the Content-Type of this header,
     * or the defaultCharset if none can be found.
     */
    public static String parseCharset(Map<String, String> headers, String defaultCharset) {
        String contentType = headers.get(HTTP.CONTENT_TYPE);
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }

        return defaultCharset;
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(Map<String, String> headers) {
        return parseCharset(headers, HTTP.DEFAULT_CONTENT_CHARSET);
    }
}
