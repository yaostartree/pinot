/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pinot.core.operator.combine;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.apache.pinot.common.exception.QueryException;
import org.apache.pinot.common.utils.DataSchema;
import org.apache.pinot.core.common.Operator;
import org.apache.pinot.core.operator.blocks.results.BaseResultsBlock;
import org.apache.pinot.core.operator.blocks.results.SelectionResultsBlock;
import org.apache.pinot.core.query.request.context.QueryContext;
import org.apache.pinot.core.query.selection.SelectionOperatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Combine operator for selection only queries.
 * <p>For query with LIMIT 0, directly use main thread to process one segment to get the data schema of the query.
 * <p>Query can be early-terminated when enough documents have been collected to fulfill the LIMIT requirement.
 * <p>NOTE: Selection order-by query with LIMIT 0 is treated as selection only query.
 */
@SuppressWarnings("rawtypes")
public class SelectionOnlyCombineOperator extends BaseCombineOperator<SelectionResultsBlock> {
  private static final Logger LOGGER = LoggerFactory.getLogger(SelectionOnlyCombineOperator.class);

  private static final String EXPLAIN_NAME = "COMBINE_SELECT";

  private final int _numRowsToKeep;

  public SelectionOnlyCombineOperator(List<Operator> operators, QueryContext queryContext,
      ExecutorService executorService) {
    super(operators, queryContext, executorService);
    _numRowsToKeep = queryContext.getLimit();
  }

  @Override
  public void close() {
  }

  @Override
  public String toExplainString() {
    return EXPLAIN_NAME;
  }

  @Override
  protected BaseResultsBlock getNextBlock() {
    // For LIMIT 0 query, only process one segment to get the data schema
    if (_numRowsToKeep == 0) {
      BaseResultsBlock resultsBlock = (BaseResultsBlock) _operators.get(0).nextBlock();
      CombineOperatorUtils.setExecutionStatistics(resultsBlock, _operators, 0, 1);
      return resultsBlock;
    }

    return super.getNextBlock();
  }

  @Override
  protected boolean isQuerySatisfied(SelectionResultsBlock resultsBlock) {
    return resultsBlock.getRows().size() == _numRowsToKeep;
  }

  @Override
  protected void mergeResultsBlocks(SelectionResultsBlock mergedBlock, SelectionResultsBlock blockToMerge) {
    DataSchema mergedDataSchema = mergedBlock.getDataSchema();
    DataSchema dataSchemaToMerge = blockToMerge.getDataSchema();
    assert mergedDataSchema != null && dataSchemaToMerge != null;
    if (!mergedDataSchema.equals(dataSchemaToMerge)) {
      String errorMessage =
          String.format("Data schema mismatch between merged block: %s and block to merge: %s, drop block to merge",
              mergedDataSchema, dataSchemaToMerge);
      // NOTE: This is segment level log, so log at debug level to prevent flooding the log.
      LOGGER.debug(errorMessage);
      mergedBlock.addToProcessingExceptions(
          QueryException.getException(QueryException.MERGE_RESPONSE_ERROR, errorMessage));
      return;
    }

    Collection<Object[]> mergedRows = mergedBlock.getRows();
    Collection<Object[]> rowsToMerge = blockToMerge.getRows();
    assert mergedRows != null && rowsToMerge != null;
    SelectionOperatorUtils.mergeWithoutOrdering(mergedRows, rowsToMerge, _numRowsToKeep);
  }
}
