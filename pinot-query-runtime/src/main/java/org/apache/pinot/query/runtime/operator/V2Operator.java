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
package org.apache.pinot.query.runtime.operator;

import java.util.List;
import org.apache.pinot.core.common.Operator;
import org.apache.pinot.core.operator.BaseOperator;
import org.apache.pinot.query.runtime.blocks.TransferableBlock;
import org.slf4j.LoggerFactory;


public abstract class V2Operator extends BaseOperator<TransferableBlock> implements AutoCloseable {
  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MailboxReceiveOperator.class);

  @Override
  public List<Operator> getChildOperators() {
    throw new UnsupportedOperationException();
  }

  abstract List<V2Operator> getV2ChildOperators();

  @Override
  public void close() {
    for (V2Operator op : getV2ChildOperators()) {
      try {
        op.close();
      } catch (Exception e) {
        LOGGER.error("Failed to close operator:" + op);
        // Continue processing because even one operator failed to be close, we should close the rest.
      }
    }
  }
}
