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
package io.trino.operator.scalar;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import io.airlift.slice.Slice;
import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;

import static io.airlift.slice.Slices.wrappedBuffer;

public final class HmacFunctions
{
    private HmacFunctions() {}

    @Description("Compute HMAC with MD5")
    @ScalarFunction
    @SqlType(StandardTypes.VARBINARY)
    public static Slice hmacMd5(@SqlType(StandardTypes.VARBINARY) Slice slice, @SqlType(StandardTypes.VARBINARY) Slice key)
    {
        return computeHash(Hashing.hmacMd5(key.getBytes()), slice);
    }

    @Description("Compute HMAC with SHA1")
    @ScalarFunction
    @SqlType(StandardTypes.VARBINARY)
    public static Slice hmacSha1(@SqlType(StandardTypes.VARBINARY) Slice slice, @SqlType(StandardTypes.VARBINARY) Slice key)
    {
        return computeHash(Hashing.hmacSha1(key.getBytes()), slice);
    }

    @Description("Compute HMAC with SHA256")
    @ScalarFunction
    @SqlType(StandardTypes.VARBINARY)
    public static Slice hmacSha256(@SqlType(StandardTypes.VARBINARY) Slice slice, @SqlType(StandardTypes.VARBINARY) Slice key)
    {
        return computeHash(Hashing.hmacSha256(key.getBytes()), slice);
    }

    @Description("Compute HMAC with SHA512")
    @ScalarFunction
    @SqlType(StandardTypes.VARBINARY)
    public static Slice hmacSha512(@SqlType(StandardTypes.VARBINARY) Slice slice, @SqlType(StandardTypes.VARBINARY) Slice key)
    {
        return computeHash(Hashing.hmacSha512(key.getBytes()), slice);
    }

    static Slice computeHash(HashFunction hash, Slice data)
    {
        HashCode result = hash.hashBytes(data.byteArray(), data.byteArrayOffset(), data.length());
        return wrappedBuffer(result.asBytes());
    }
}
