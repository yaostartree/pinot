

package org.apache.pinot.query.runtime.operator;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.calcite.sql.SqlKind;
import org.apache.pinot.common.datablock.DataBlock;
import org.apache.pinot.common.exception.QueryException;
import org.apache.pinot.common.utils.DataSchema;
import org.apache.pinot.core.common.Operator;
import org.apache.pinot.query.planner.logical.RexExpression;
import org.apache.pinot.query.runtime.blocks.TransferableBlock;
import org.apache.pinot.query.runtime.blocks.TransferableBlockUtils;
import org.apache.pinot.spi.data.FieldSpec;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class FilterOperatorTest {
  private AutoCloseable _mocks;
  @Mock
  private Operator<TransferableBlock> _upstreamOperator;

  @BeforeMethod
  public void setUp() {
    _mocks = MockitoAnnotations.openMocks(this);
  }

  @AfterMethod
  public void tearDown()
      throws Exception {
    _mocks.close();
  }

  @Test
  public void testUpstreamErrorBlock() {
    Mockito.when(_upstreamOperator.nextBlock())
        .thenReturn(TransferableBlockUtils.getErrorTransferableBlock(new Exception("filterError")));
    RexExpression booleanLiteral = new RexExpression.Literal(FieldSpec.DataType.BOOLEAN, true);
    DataSchema inputSchema = new DataSchema(new String[]{"boolCol"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.BOOLEAN
    });
    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, booleanLiteral);
    TransferableBlock errorBlock = op.getNextBlock();
    Assert.assertTrue(errorBlock.isErrorBlock());
    DataBlock error = errorBlock.getDataBlock();
    Assert.assertTrue(error.getExceptions().get(QueryException.UNKNOWN_ERROR_CODE).contains("filterError"));
  }

  @Test
  public void testUpstreamEos() {
    RexExpression booleanLiteral = new RexExpression.Literal(FieldSpec.DataType.BOOLEAN, true);

    DataSchema inputSchema = new DataSchema(new String[]{"intCol"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT
    });
    Mockito.when(_upstreamOperator.nextBlock()).thenReturn(OperatorTestUtil.block(inputSchema, new Object[]{0}))
        .thenReturn(TransferableBlockUtils.getEndOfStreamTransferableBlock());
    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, booleanLiteral);
    TransferableBlock dataBlock = op.getNextBlock();
    Assert.assertFalse(dataBlock.isErrorBlock());
    List<Object[]> result = dataBlock.getContainer();
    Assert.assertEquals(result.size(), 1);
    Assert.assertEquals(result.get(0)[0], 0);
    dataBlock = op.getNextBlock();
    Assert.assertTrue(dataBlock.isEndOfStreamBlock());
  }

  @Test
  public void testTrueBooleanFilter() {
    RexExpression booleanLiteral = new RexExpression.Literal(FieldSpec.DataType.BOOLEAN, true);

    DataSchema inputSchema = new DataSchema(new String[]{"intCol"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT
    });
    Mockito.when(_upstreamOperator.nextBlock())
        .thenReturn(OperatorTestUtil.block(inputSchema, new Object[]{0}, new Object[]{1}))
        .thenReturn(TransferableBlockUtils.getEndOfStreamTransferableBlock());
    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, booleanLiteral);
    TransferableBlock dataBlock = op.getNextBlock();
    Assert.assertFalse(dataBlock.isErrorBlock());
    List<Object[]> result = dataBlock.getContainer();
    Assert.assertEquals(result.size(), 2);
    Assert.assertEquals(result.get(0)[0], 0);
    Assert.assertEquals(result.get(1)[0], 1);
  }

  @Test
  public void testFalseBooleanFilter() {
    RexExpression booleanLiteral = new RexExpression.Literal(FieldSpec.DataType.BOOLEAN, false);

    DataSchema inputSchema = new DataSchema(new String[]{"intCol"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT
    });
    Mockito.when(_upstreamOperator.nextBlock())
        .thenReturn(OperatorTestUtil.block(inputSchema, new Object[]{1}, new Object[]{2}));
    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, booleanLiteral);
    TransferableBlock dataBlock = op.getNextBlock();
    Assert.assertFalse(dataBlock.isErrorBlock());
    List<Object[]> result = dataBlock.getContainer();
    Assert.assertTrue(result.isEmpty());
  }

  @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = ".*boolean literal.*")
  public void testBadTypeLiteralFilter() {
    RexExpression booleanLiteral = new RexExpression.Literal(FieldSpec.DataType.STRING, "false");
    DataSchema inputSchema = new DataSchema(new String[]{"intCol"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT
    });
    Mockito.when(_upstreamOperator.nextBlock())
        .thenReturn(OperatorTestUtil.block(inputSchema, new Object[]{1}, new Object[]{2}));
    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, booleanLiteral);
  }

  @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = ".*Input has to be "
      + "boolean type.*")
  public void testInputRefFilterBadType() {
    RexExpression ref0 = new RexExpression.InputRef(0);
    DataSchema inputSchema = new DataSchema(new String[]{"intCol"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT
    });
    Mockito.when(_upstreamOperator.nextBlock())
        .thenReturn(OperatorTestUtil.block(inputSchema, new Object[]{1}, new Object[]{2}));
    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, ref0);
  }

  @Test
  public void testInputRefFilter() {
    RexExpression ref1 = new RexExpression.InputRef(1);
    DataSchema inputSchema = new DataSchema(new String[]{"intCol", "boolCol"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.INT, DataSchema.ColumnDataType.BOOLEAN
    });
    Mockito.when(_upstreamOperator.nextBlock())
        .thenReturn(OperatorTestUtil.block(inputSchema, new Object[]{1, true}, new Object[]{2, false}));
    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, ref1);
    TransferableBlock dataBlock = op.getNextBlock();
    Assert.assertFalse(dataBlock.isErrorBlock());
    List<Object[]> result = dataBlock.getContainer();
    Assert.assertEquals(result.size(), 1);
    Assert.assertEquals(result.get(0)[0], 1);
    Assert.assertEquals(result.get(0)[1], true);
  }

  @Test
  public void testAndFilter() {
    DataSchema inputSchema = new DataSchema(new String[]{"boolCol0", "boolCol1"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.BOOLEAN, DataSchema.ColumnDataType.BOOLEAN
    });
    Mockito.when(_upstreamOperator.nextBlock()).thenReturn(
        OperatorTestUtil.block(inputSchema, new Object[]{true, true}, new Object[]{false, false},
            new Object[]{true, false}));
    RexExpression.FunctionCall andCall = new RexExpression.FunctionCall(SqlKind.AND, FieldSpec.DataType.BOOLEAN, "AND",
        ImmutableList.of(new RexExpression.InputRef(0), new RexExpression.InputRef(1)));

    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, andCall);
    TransferableBlock dataBlock = op.getNextBlock();
    Assert.assertFalse(dataBlock.isErrorBlock());
    List<Object[]> result = dataBlock.getContainer();
    Assert.assertEquals(result.size(), 1);
    Assert.assertEquals(result.get(0)[0], true);
    Assert.assertEquals(result.get(0)[1], true);
  }

  @Test
  public void testOrFilter() {
    DataSchema inputSchema = new DataSchema(new String[]{"boolCol0", "boolCol1"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.BOOLEAN, DataSchema.ColumnDataType.BOOLEAN
    });
    Mockito.when(_upstreamOperator.nextBlock()).thenReturn(
        OperatorTestUtil.block(inputSchema, new Object[]{true, true}, new Object[]{false, false},
            new Object[]{true, false}));
    RexExpression.FunctionCall orCall = new RexExpression.FunctionCall(SqlKind.OR, FieldSpec.DataType.BOOLEAN, "OR",
        ImmutableList.of(new RexExpression.InputRef(0), new RexExpression.InputRef(1)));

    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, orCall);
    TransferableBlock dataBlock = op.getNextBlock();
    Assert.assertFalse(dataBlock.isErrorBlock());
    List<Object[]> result = dataBlock.getContainer();
    Assert.assertEquals(result.size(), 2);
    Assert.assertEquals(result.get(0)[0], true);
    Assert.assertEquals(result.get(0)[1], true);
    Assert.assertEquals(result.get(1)[0], true);
    Assert.assertEquals(result.get(1)[1], false);
  }

  @Test
  public void testNotFilter() {
    DataSchema inputSchema = new DataSchema(new String[]{"boolCol0", "boolCol1"}, new DataSchema.ColumnDataType[]{
        DataSchema.ColumnDataType.BOOLEAN});
    Mockito.when(_upstreamOperator.nextBlock()).thenReturn(
        OperatorTestUtil.block(inputSchema, new Object[]{true, true}, new Object[]{false, false},
            new Object[]{true, false}));
    RexExpression.FunctionCall orCall = new RexExpression.FunctionCall(SqlKind.OR, FieldSpec.DataType.BOOLEAN, "OR",
        ImmutableList.of(new RexExpression.InputRef(0)));

    FilterOperator op = new FilterOperator(_upstreamOperator, inputSchema, orCall);
    TransferableBlock dataBlock = op.getNextBlock();
    Assert.assertFalse(dataBlock.isErrorBlock());
    List<Object[]> result = dataBlock.getContainer();
    Assert.assertEquals(result.size(), 2);
    Assert.assertEquals(result.get(0)[0], true);
    Assert.assertEquals(result.get(0)[1], true);
    Assert.assertEquals(result.get(1)[0], true);
    Assert.assertEquals(result.get(1)[1], false);
  }
}