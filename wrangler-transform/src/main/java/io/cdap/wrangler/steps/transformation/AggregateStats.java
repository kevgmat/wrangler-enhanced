/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

 package io.cdap.wrangler.steps.transformation;

 import io.cdap.wrangler.api.Arguments;
 import io.cdap.wrangler.api.DirectiveExecutionException;
 import io.cdap.wrangler.api.DirectiveParseException;
 import io.cdap.wrangler.api.Executor;
 import io.cdap.wrangler.api.ExecutorContext;
 import io.cdap.wrangler.api.Row;
 import io.cdap.wrangler.api.annotations.Usage;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 
 

 /**
 * A directive that aggregates statistics such as count, average, min, and max for specified columns.
 * Used in data transformation pipelines.
 */

@Usage("aggregate-stats <column> <operation> - Aggregates statistics (min, max, avg, sum, count) over a column")
public class AggregateStats implements Executor {

  private String column;
  private String operation;

  @Override
  public void initialize(Arguments arguments) throws DirectiveParseException {
  if (!arguments.contains("0") || !arguments.contains("1")) {
    throw new DirectiveParseException("aggregate-stats requires exactly two arguments: <column> <operation>");
  }
  column = arguments.value("0");
  operation = arguments.value("1").toString().toLowerCase();
  }


  public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException {
    List<Double> values = new ArrayList<>();

    for (Row row : rows) {
      Object val = row.getValue(column);
      if (val instanceof Number) {
        values.add(((Number) val).doubleValue());
      } else {
        throw new DirectiveExecutionException(
          String.format("Value in column '%s' is not numeric: %s", column, val)
        );
      }
    }

    double result;
    switch (operation) {
      case "min":
        result = values.stream().min(Double::compareTo).orElse(Double.NaN);
        break;
      case "max":
        result = values.stream().max(Double::compareTo).orElse(Double.NaN);
        break;
      case "sum":
        result = values.stream().mapToDouble(Double::doubleValue).sum();
        break;
      case "avg":
        result = values.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
        break;
      case "count":
        result = values.size();
        break;
      default:
        throw new DirectiveExecutionException("Unsupported operation: " + operation);
    }

    Row resultRow = new Row();
    resultRow.add(String.format("%s_%s", column, operation), result);
    return Collections.singletonList(resultRow);
  }

  @Override
  public List<Row> execute(Object input, ExecutorContext context) throws DirectiveExecutionException {
    return execute((List<Row>) input, context);
  }


  public void destroy() {
    // Nothing to clean up
  }
}
