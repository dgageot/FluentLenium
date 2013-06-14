/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.integration.localtest;

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.common.io.*;
import com.sun.net.httpserver.*;

class TestWebServer implements HttpHandler {
    private static final int TRY_COUNT = 10;
    private static final int DEFAULT_PORT = 8183;

    private final Random random = new Random();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString();

        String page = uri.substring(uri.lastIndexOf('/') + 1);
        if (page.isEmpty()) {
            page = "index.html";
        }

        byte[] body = Resources.toByteArray(Resources.getResource("html/" + page));
        exchange.getResponseHeaders().add("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, body.length);
        exchange.getResponseBody().write(body);
        exchange.close();
    }

    public int startOnRandomPort() throws IOException {
        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                int port = randomPort();

                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/", this);
                server.start();

                return port;
            } catch (Exception e) {
                System.err.println("Unable to bind server: " + e);
            }
        }

        throw new IllegalStateException("Unable to start server");
    }

    private synchronized int randomPort() {
        return DEFAULT_PORT + random.nextInt(1000);
    }
}
