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
package org.apache.pinot.common.type;

import org.apache.pinot.common.request.Literal;
import org.apache.pinot.spi.data.FieldSpec;


// TODO: Move all data types here.
public class DataTypeFactory {
  private DataTypeFactory() {
  }

  // TODO: Support all data types.
  // Convert literal thrift field type from thrift to data type.
  public static FieldSpec.DataType createDataType(Literal._Fields fields) {
    switch (fields) {
      case LONG_VALUE:
        return FieldSpec.DataType.LONG;
      case BOOL_VALUE:
        return FieldSpec.DataType.BOOLEAN;
      case DOUBLE_VALUE:
        return FieldSpec.DataType.DOUBLE;
      case STRING_VALUE:
        return FieldSpec.DataType.STRING;
      case NULL_VALUE:
        return FieldSpec.DataType.UNKNOWN;
      default:
        throw new UnsupportedOperationException("Unsupported literal type:" + fields);
    }
  }
}
