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
package org.fluentlenium.adapter.util;

import org.fluentlenium.core.FluentAdapter;

public final class SharedDriverHelper {

    public static SharedDriver getSharedBrowser(Class classe) {
        Class<?> cls;
        for (cls = classe; FluentAdapter.class.isAssignableFrom(cls); cls = cls.getSuperclass()) {
            if (cls.isAnnotationPresent(SharedDriver.class)) {
                return cls.getAnnotation(SharedDriver.class);
            }
        }
        return null;
    }

    public boolean isSharedDriver(Class classe) {
        return (getSharedBrowser(classe) != null);
    }

    public static boolean isDeleteCookies(Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.deleteCookies());
    }

    public static boolean isSharedDriverOnce(Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.ONCE);
    }

    public static boolean isSharedDriverPerClass(Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_CLASS);
    }

    public static boolean isSharedDriverPerMethod(Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_METHOD);
    }

    public static boolean isDefaultSharedDriver(Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser == null);
    }
}