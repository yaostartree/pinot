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
package org.apache.pinot.common.request.context;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;
import javax.annotation.Nullable;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pinot.common.request.Literal;
import org.apache.pinot.common.utils.PinotDataType;
import org.apache.pinot.spi.data.FieldSpec;


/**
 * The {@code LiteralContext} class represents a literal in the query.
 * <p>This includes both value and type information. We translate thrift literal to this representation in server.
 * Currently, only Boolean literal is correctly encoded in thrift and passed in.
 * All integers are encoded as LONG in thrift, and the other numerical types are encoded as DOUBLE.
 * The remaining types are encoded as STRING.
 */
public class LiteralContext {
  // TODO: Support all of the types for sql.
  private FieldSpec.DataType _type;
  private Object _value;

  // TODO: Deprecate the usage case for this function.
  @VisibleForTesting
  static private Pair<FieldSpec.DataType, Object> inferLiteralDataTypeAndValue(String literal) {
    // Try to interpret the literal as number
    try {
      Number number = NumberUtils.createNumber(literal);
      if  (number instanceof BigDecimal || number instanceof BigInteger) {
        return ImmutablePair.of(FieldSpec.DataType.BIG_DECIMAL, new BigDecimal(literal));
      } else {
        return ImmutablePair.of(FieldSpec.DataType.STRING, literal);
      }
    } catch (Exception e) {
      // Ignored
    }

    // Try to interpret the literal as TIMESTAMP
    try {
      Timestamp timestamp = Timestamp.valueOf(literal);
      return ImmutablePair.of(FieldSpec.DataType.TIMESTAMP, timestamp);
    } catch (Exception e) {
      // Ignored
    }
    return ImmutablePair.of(FieldSpec.DataType.STRING, literal);
  }

  public LiteralContext(Literal literal) {
    Preconditions.checkState(literal.getFieldValue() != null,
        "Field value cannot be null for field:" + literal.getSetField());
    switch (literal.getSetField()){
      case BOOL_VALUE:
        _type = FieldSpec.DataType.BOOLEAN;
        _value = literal.getFieldValue();
        break;
      case DOUBLE_VALUE:
        _type = FieldSpec.DataType.DOUBLE;
        _value = literal.getFieldValue();
        break;
      case LONG_VALUE:
        _type = FieldSpec.DataType.LONG;
        _value = literal.getFieldValue();
        break;
      case NULL_VALUE:
        _type = FieldSpec.DataType.UNKNOWN;
        _value = null;
        break;
      case STRING_VALUE:
        Pair<FieldSpec.DataType, Object> typeAndValue = inferLiteralDataTypeAndValue(literal.getFieldValue().toString());
        _type = typeAndValue.getLeft();
        _value = typeAndValue.getRight();
        break;
      default:
        throw new UnsupportedOperationException("Unsupported data type:" + literal.getSetField());
    }
  }

  public FieldSpec.DataType getType() {
    return _type;
  }

  public BigDecimal getBigDecimalValue() {
    if(_type == FieldSpec.DataType.BIG_DECIMAL){
      return (BigDecimal) _value;
    }
    if (_type == FieldSpec.DataType.BOOLEAN) {
      return PinotDataType.BOOLEAN.toBigDecimal(_value);
    }
    if (_type.isNumeric()) {
      return new BigDecimal(_value.toString());
    }
    if (_type == FieldSpec.DataType.TIMESTAMP) {
      // inferLiteralDataType successfully interpreted the literal as TIMESTAMP. _bigDecimalLiteral is populated and
      // assigned to _longLiteral.
      return PinotDataType.TIMESTAMP.toBigDecimal(Timestamp.valueOf(_value.toString()));
    }
    return BigDecimal.ZERO;
  }

  @Nullable
  public Object getValue() {
    return _value;
  }

  // This ctor is only used for special handling in subquery.
  public LiteralContext(FieldSpec.DataType type, Object value) {
    _type = type;
    if(type == FieldSpec.DataType.UNKNOWN){
      _value = null;
    } else {
      _value = value;
    }
  }

  @Override
  public int hashCode() {
    return 31 * Objects.hashCode(_value) + Objects.hashCode(_type);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LiteralContext)) {
      return false;
    }
    LiteralContext that = (LiteralContext) o;
    return _type.equals(that._type) && Objects.equals(_value, that._value);
  }

  @Override
  public String toString() {
    // TODO: print out the type.
    return '\'' + String.valueOf(_value) + '\'';
  }
}
