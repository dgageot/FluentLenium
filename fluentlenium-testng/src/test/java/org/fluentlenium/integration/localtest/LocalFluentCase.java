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

import org.fluentlenium.adapter.FluentTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public abstract class LocalFluentCase extends FluentTestNg {
    private static String url;

    public String getDefaultBaseUrl() {
        if (url != null) {
            return url;
        }

        TestWebServer testWebServer = new TestWebServer();
        try {
            int port = testWebServer.startOnRandomPort();
            url = "http://localhost:" + port + "/";
            return url;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to start http server", e);
        }
    }

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
}

