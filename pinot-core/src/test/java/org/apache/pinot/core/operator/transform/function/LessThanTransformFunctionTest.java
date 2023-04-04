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
package org.apache.pinot.core.operator.transform.function;

import org.apache.pinot.common.request.context.ExpressionContext;
import org.apache.pinot.common.request.context.RequestContextUtils;
import org.roaringbitmap.RoaringBitmap;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LessThanTransformFunctionTest extends BinaryOperatorTransformFunctionTest {

  @Override
  boolean getExpectedValue(int compareResult) {
    return compareResult < 0;
  }

  @Override
  String getFunctionName() {
    return new LessThanTransformFunction().getName();
  }

  @Test
  public void testLessThanNullLiteral() {
    ExpressionContext expression =
        RequestContextUtils.getExpression(String.format("less_than(null, %s)", INT_SV_NULL_COLUMN));
    TransformFunction transformFunction = TransformFunctionFactory.get(expression, _dataSourceMap);
    Assert.assertTrue(transformFunction instanceof LessThanTransformFunction);
    Assert.assertEquals(transformFunction.getName(), "less_than");
    int[] expectedValues = new int[NUM_ROWS];
<<<<<<< HEAD
=======
    for (int i = 0; i < NUM_ROWS; i++) {
      expectedValues[i] = 0;
    }
>>>>>>> 39f12684e1 (test)
    RoaringBitmap roaringBitmap = new RoaringBitmap();
    roaringBitmap.add(0L, NUM_ROWS);
    testTransformFunctionWithNull(transformFunction, expectedValues, roaringBitmap);
  }

  @Test
  public void testLessThanNullColumn() {
    ExpressionContext expression =
        RequestContextUtils.getExpression(String.format("less_than(%s, %s)", INT_SV_NULL_COLUMN, INT_SV_COLUMN));
    TransformFunction transformFunction = TransformFunctionFactory.get(expression, _dataSourceMap);
    Assert.assertTrue(transformFunction instanceof LessThanTransformFunction);
    Assert.assertEquals(transformFunction.getName(), "less_than");
    int[] expectedValues = new int[NUM_ROWS];
    RoaringBitmap roaringBitmap = new RoaringBitmap();
    for (int i = 0; i < NUM_ROWS; i++) {
      if (i % 2 == 0) {
<<<<<<< HEAD
        expectedValues[i] = 0;
      } else {
=======
        expectedValues[i] =  0;
      } else {
        expectedValues[i] =  1;
>>>>>>> 39f12684e1 (test)
        roaringBitmap.add(i);
      }
    }
    testTransformFunctionWithNull(transformFunction, expectedValues, roaringBitmap);
  }
}
