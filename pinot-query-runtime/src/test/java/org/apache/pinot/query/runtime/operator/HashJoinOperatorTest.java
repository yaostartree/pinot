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

import io.netty.handler.codec.serialization.ObjectEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.crypto.Data;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.sql.SqlKind;
import org.apache.pinot.common.datablock.MetadataBlock;
import org.apache.pinot.common.utils.DataSchema;
import org.apache.pinot.core.common.Operator;
import org.apache.pinot.core.operator.BaseOperator;
import org.apache.pinot.query.planner.logical.RexExpression;
import org.apache.pinot.query.planner.partitioning.FieldSelectionKeySelector;
import org.apache.pinot.query.planner.stage.JoinNode;
import org.apache.pinot.query.runtime.blocks.TransferableBlock;
import org.apache.pinot.spi.data.FieldSpec;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

public class HashJoinOperatorTest {
  private AutoCloseable _mocks;

  @Mock
  private Operator<TransferableBlock> _leftOperator;

  @Mock
  private Operator<TransferableBlock> _rightOperator;

  @BeforeMethod
  public void setUp() {
    _mocks = MockitoAnnotations.openMocks(this);
  }

  @AfterMethod
  public void tearDown()
      throws Exception {
    _mocks.close();
  }

  private static JoinNode.JoinKeys getJoinKeys(List<Integer> leftIdx, List<Integer> rightIdx) {
    FieldSelectionKeySelector leftSelect = new FieldSelectionKeySelector(leftIdx);
    FieldSelectionKeySelector rightSelect = new FieldSelectionKeySelector(rightIdx);
    return new JoinNode.JoinKeys(leftSelect, rightSelect);
  }

  @Test
  public void testHashJoinKeyCollisionInnerJoin() {
    DataSchema leftSchema = new DataSchema(new String[]{"int_col", "string_cl"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING});
    DataSchema rightSchema = new DataSchema(new String[]{"int_col", "string_cl"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING});

    List<RexExpression> joinClauses = new ArrayList<>();
    Mockito.when(_leftOperator.nextBlock())
        .thenReturn(block(leftSchema, new Object[]{1, "Aa"}))
        .thenReturn(TransferableBlockUtils.getNoOpTransferableBlock());

    DataSchema resultSchema = new DataSchema(new String[]{"foo", "bar", "foo", "bar"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING, DataSchema.ColumnDataType.INT,
        DataSchema.ColumnDataType.STRING
    });
    HashJoinOperator join =
        new HashJoinOperator(leftOperator, rightOperator, resultSchema, getJoinKeys(Arrays.asList(1), Arrays.asList(1)),
            joinClauses, JoinRelType.INNER);

    TransferableBlock result = join.nextBlock();
    while (result.isNoOpBlock()) {
      result = join.nextBlock();
    }
    List<Object[]> resultRows = result.getContainer();
    List<Object[]> expectedRows =
        Arrays.asList(new Object[]{1, "Aa", 1, "Aa"}, new Object[]{2, "BB", 2, "BB"}, new Object[]{2, "BB", 3, "BB"},
            new Object[]{3, "BB", 2, "BB"}, new Object[]{3, "BB", 3, "BB"});
    Assert.assertEquals(expectedRows.size(), resultRows.size());
    Assert.assertEquals(expectedRows.get(0), resultRows.get(0));
    Assert.assertEquals(expectedRows.get(1), resultRows.get(1));
    Assert.assertEquals(expectedRows.get(2), resultRows.get(2));
    Assert.assertEquals(expectedRows.get(3), resultRows.get(3));
    Assert.assertEquals(expectedRows.get(4), resultRows.get(4));
  }

  @Test
  public void testInnerJoin() {
    BaseOperator<TransferableBlock> leftOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_1);
    BaseOperator<TransferableBlock> rightOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_2);
    List<RexExpression> joinClauses = new ArrayList<>();

    DataSchema resultSchema = new DataSchema(new String[]{"foo", "bar", "foo", "bar"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING, DataSchema.ColumnDataType.INT,
        DataSchema.ColumnDataType.STRING
    });
    HashJoinOperator join =
        new HashJoinOperator(leftOperator, rightOperator, resultSchema, getJoinKeys(Arrays.asList(1), Arrays.asList(1)),
            joinClauses, JoinRelType.INNER);

    TransferableBlock result = join.nextBlock();
    while (result.isNoOpBlock()) {
      result = join.nextBlock();
    }
    List<Object[]> resultRows = result.getContainer();
    Object[] expRow = new Object[]{1, "Aa", 2, "Aa"};
    List<Object[]> expectedRows = new ArrayList<>();
    expectedRows.add(expRow);
    Assert.assertEquals(expectedRows.size(), resultRows.size());
    Assert.assertEquals(expectedRows.get(0), resultRows.get(0));
  }

  @Test
  public void testLeftJoin() {
    BaseOperator<TransferableBlock> leftOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_1);
    BaseOperator<TransferableBlock> rightOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_2);

    List<RexExpression> joinClauses = new ArrayList<>();
    DataSchema resultSchema = new DataSchema(new String[]{"foo", "bar", "foo", "bar"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING, DataSchema.ColumnDataType.INT,
        DataSchema.ColumnDataType.STRING
    });
    HashJoinOperator join =
        new HashJoinOperator(leftOperator, rightOperator, resultSchema, getJoinKeys(Arrays.asList(1), Arrays.asList(1)),
            joinClauses, JoinRelType.LEFT);

    TransferableBlock result = join.nextBlock();
    while (result.isNoOpBlock()) {
      result = join.nextBlock();
    }
    List<Object[]> resultRows = result.getContainer();
    List<Object[]> expectedRows = Arrays.asList(new Object[]{1, "Aa", 2, "Aa"}, new Object[]{2, "BB", null, null},
        new Object[]{3, "BB", null, null});
    Assert.assertEquals(expectedRows.size(), resultRows.size());
    Assert.assertEquals(expectedRows.get(0), resultRows.get(0));
    Assert.assertEquals(expectedRows.get(1), resultRows.get(1));
    Assert.assertEquals(expectedRows.get(2), resultRows.get(2));
  }

  @Test
  public void testRightJoinNotSupported(){
    List<Object[]> rows = Arrays.asList(new Object[]{1, "Aa"}, new Object[]{2, "BB"}, new Object[]{3, "BB"});

    DataSchema inSchema = new DataSchema(new String[]{"group", "arg"}, new ColumnDataType[]{INT, INT});


    when(_leftOperator.nextBlock()).thenReturn(OperatorTestUtil.getRowDataBlock(rows))
        .thenReturn(OperatorTestUtil.getEndOfStreamRowBlock());
    when(_rightOperator.nextBlock()).thenReturn(OperatorTestUtil.getRowDataBlock(rows))
        .thenReturn(OperatorTestUtil.getEndOfStreamRowBlock());

    List<RexExpression> joinClauses = new ArrayList<>();
    DataSchema resultSchema = new DataSchema(new String[]{"foo", "bar", "foo", "bar"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING, DataSchema.ColumnDataType.INT,
        DataSchema.ColumnDataType.STRING
    });
    HashJoinOperator join = new HashJoinOperator(leftOperator, rightOperator, resultSchema,
        getJoinKeys(Arrays.asList(1), Arrays.asList(1)), joinClauses, JoinRelType.RIGHT);
    TransferableBlock result = join.nextBlock();
    while (result.isNoOpBlock()) {
      result = join.nextBlock();
    }
    Assert.assertTrue(result.isErrorBlock());
    MetadataBlock errorBlock = (MetadataBlock) result.getDataBlock();
    Assert.assertEquals(errorBlock.getExceptions().size(), 1);
    Assert.assertEquals(errorBlock.getExceptions().get(1000), "Right join is not supported");
  }

  @Test
  public void testSemiJoinNotSupported(){
    BaseOperator<TransferableBlock> leftOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_1);
    BaseOperator<TransferableBlock> rightOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_2);

    List<RexExpression> joinClauses = new ArrayList<>();
    DataSchema resultSchema = new DataSchema(new String[]{"foo", "bar", "foo", "bar"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING, DataSchema.ColumnDataType.INT,
        DataSchema.ColumnDataType.STRING
    });
    HashJoinOperator join = new HashJoinOperator(leftOperator, rightOperator, resultSchema,
        getJoinKeys(Arrays.asList(1), Arrays.asList(1)), joinClauses, JoinRelType.SEMI);
    TransferableBlock result = join.nextBlock();
    while (result.isNoOpBlock()) {
      result = join.nextBlock();
    }
    Assert.assertTrue(result.isErrorBlock());
    MetadataBlock errorBlock = (MetadataBlock) result.getDataBlock();
    Assert.assertEquals(errorBlock.getExceptions().size(), 1);
    Assert.assertEquals(errorBlock.getExceptions().get(1000), "Semi join is not supported");
  }

  @Test
  public void testInequiJoin(){
    BaseOperator<TransferableBlock> leftOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_1);
    BaseOperator<TransferableBlock> rightOperator = OperatorTestUtil.getOperator(OperatorTestUtil.OP_2);
    DataSchema resultSchema = new DataSchema(new String[]{"foo", "bar", "foo", "bar"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.STRING, DataSchema.ColumnDataType.INT,
        DataSchema.ColumnDataType.STRING
    });
    List<RexExpression> joinClauses = new ArrayList<>();
    List<RexExpression> functionOperands = new ArrayList<>();
    functionOperands.add(new RexExpression.InputRef(0));
    functionOperands.add(new RexExpression.Literal(FieldSpec.DataType.INT, 2));
    joinClauses.add(
        new RexExpression.FunctionCall(SqlKind.NOT_EQUALS, FieldSpec.DataType.INT, "NOT_EQUALS", functionOperands));
    HashJoinOperator join = new HashJoinOperator(leftOperator, rightOperator, resultSchema,
        getJoinKeys(new ArrayList<>(),new ArrayList<>()), joinClauses, JoinRelType.INNER);

    TransferableBlock result = join.nextBlock();
    while (result.isNoOpBlock()) {
      result = join.nextBlock();
    }
    List<Object[]> rows = result.getContainer();

  }
}
