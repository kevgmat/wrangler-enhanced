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
 * Represents a time duration (e.g., 10ms, 2s, 3h).
 */
@PublicEvolving
public class TimeDuration implements Token {
  private final long millis;
  private final String value;

  public TimeDuration(String value) {
    this.value = value;
    this.millis = parseDuration(value);
  }

  private long parseDuration(String val) {
    val = val.trim().toLowerCase();

    if (val.endsWith("ms")) {
      return Long.parseLong(val.substring(0, val.length() - 2));
    } else if (val.endsWith("s")) {
      return Long.parseLong(val.substring(0, val.length() - 1)) * 1000L;
    } else if (val.endsWith("m")) {
      return Long.parseLong(val.substring(0, val.length() - 1)) * 60 * 1000L;
    } else if (val.endsWith("h")) {
      return Long.parseLong(val.substring(0, val.length() - 1)) * 60 * 60 * 1000L;
    } else if (val.endsWith("d")) {
      return Long.parseLong(val.substring(0, val.length() - 1)) * 24 * 60 * 60 * 1000L;
    } else {
      throw new IllegalArgumentException("Invalid time format: " + val);
    }
  }

  public long getMillis() {
    return millis;
  }

  @Override
  public Object value() {
    return value;
  }

  @Override
  public TokenType type() {
    return TokenType.TIME_DURATION;
  }

  @Override
  public JsonElement toJson() {
    JsonObject object = new JsonObject();
    object.addProperty("type", TokenType.TIME_DURATION.name());
    object.addProperty("value", value);
    return object;
  }
}
