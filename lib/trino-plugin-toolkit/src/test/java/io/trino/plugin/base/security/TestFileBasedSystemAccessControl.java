/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.base.security;

import com.google.common.collect.ImmutableMap;
import io.trino.spi.security.SystemAccessControl;

import java.nio.file.Path;
import java.util.Map;

public class TestFileBasedSystemAccessControl
        extends BaseFileBasedSystemAccessControlTest
{
    @Override
    protected SystemAccessControl newFileBasedSystemAccessControl(Path configFile, Map<String, String> properties)
    {
        return newFileBasedSystemAccessControl(ImmutableMap.<String, String>builder()
                .putAll(properties)
                .put("security.config-file", configFile.toAbsolutePath().toString())
                .put("bootstrap.quiet", "true")
                .buildOrThrow());
    }
}
