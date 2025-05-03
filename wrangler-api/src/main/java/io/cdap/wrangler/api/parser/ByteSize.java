/*
 * Copyright © 2025 Cask Data, Inc.
 *
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
package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cdap.wrangler.api.annotations.PublicEvolving;

/**
 * Represents a size in bytes (e.g., 1K, 5M, 2G).
 */
@PublicEvolving
public class ByteSize implements Token {
  private final long bytes;
  private final String value;

  public ByteSize(String value) {
    this.value = value;
    this.bytes = parseBytes(value);
  }

  private long parseBytes(String val) {
    val = val.trim().toUpperCase();
    if (val.endsWith("B")) {
      val = val.substring(0, val.length() - 1); // Remove trailing 'B'
    }

    long multiplier = 1;
    if (val.endsWith("K")) {
      multiplier = 1024L;
      val = val.substring(0, val.length() - 1);
    } else if (val.endsWith("M")) {
      multiplier = 1024L * 1024;
      val = val.substring(0, val.length() - 1);
    } else if (val.endsWith("G")) {
      multiplier = 1024L * 1024 * 1024;
      val = val.substring(0, val.length() - 1);
    } else if (val.endsWith("T")) {
      multiplier = 1024L * 1024 * 1024 * 1024;
      val = val.substring(0, val.length() - 1);
    }

    return (long) (Double.parseDouble(val) * multiplier);
  }

  public long getBytes() {
    return bytes;
  }

  @Override
  public Object value() {
    return value;
  }

  @Override
  public TokenType type() {
    return TokenType.BYTE_SIZE;
  }

  @Override
  public JsonElement toJson() {
    JsonObject object = new JsonObject();
    object.addProperty("type", TokenType.BYTE_SIZE.name());
    object.addProperty("value", value);
    return object;
  }
}
