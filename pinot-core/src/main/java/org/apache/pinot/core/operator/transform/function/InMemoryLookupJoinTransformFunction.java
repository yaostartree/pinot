package org.apache.pinot.core.operator.transform.function;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pinot.core.data.manager.offline.InMemoryTable;
import org.apache.pinot.core.operator.blocks.ProjectionBlock;
import org.apache.pinot.core.operator.transform.TransformResultMetadata;
import org.apache.pinot.core.query.request.context.QueryContext;
import org.apache.pinot.segment.spi.datasource.DataSource;
import org.apache.pinot.spi.data.FieldSpec;
import org.apache.pinot.spi.data.readers.PrimaryKey;
import org.apache.pinot.spi.utils.ByteArray;


public class InMemoryLookupJoinTransformFunction extends BaseTransformFunction {
  public static final String FUNCTION_NAME = "InMemoryLookUpJoin";

  private final List<String> _joinKeys = new ArrayList<>();
  private final List<FieldSpec> _joinValueFieldSpecs = new ArrayList<>();
  private TransformFunction _joinValueFunctions = null;

  private HashMap<PrimaryKey, Object[]> _keyValuesMap;

  private  InMemoryTable _inMemoryTable;
  private String _filterFunc;

  private TransformFunction _condCol1;

  private String _condCol2;

  @Override
  public String getName() {
    return FUNCTION_NAME;
  }

  @Override
  public void init(List<TransformFunction> arguments, Map<String, DataSource> dataSourceMap, QueryContext context) {
    // Check that there are correct number of arguments
    Preconditions.checkArgument(arguments.size() >= 4,
        "At least 4 arguments are required for LOOKUP transform function: "
            + "LOOKUPJOIN(inMemoryTableName, joinKey1, joinKey2, filterFunc, condCol1, condCol2)");

    TransformFunction inMemoryTableFunc = arguments.get(0);
    Preconditions.checkArgument(inMemoryTableFunc instanceof LiteralTransformFunction,
        "First argument must be a literal(string) representing the dimension table name");
    // Lookup parameters
    _inMemoryTable = context.getInMemoryTable(((LiteralTransformFunction) inMemoryTableFunc).getLiteral());
    // Only one join key is allowed.
    _joinValueFunctions  = arguments.get(1);
    TransformFunction inMemoryJoinKey = arguments.get(2);
    Preconditions.checkArgument(inMemoryJoinKey instanceof LiteralTransformFunction,
        "JoinKey argument must be a literal(string)");
    _keyValuesMap =
        _inMemoryTable.getHashMap(ImmutableList.of(((LiteralTransformFunction) inMemoryJoinKey).getLiteral()));

    _filterFunc = ((LiteralTransformFunction) arguments.get(3)).getLiteral();

    _condCol1 = arguments.get(4);

    System.out.println("liuyao:" + _condCol1);

    _condCol2 = ((LiteralTransformFunction) arguments.get(5)).getLiteral();
  }

  @Override
  public TransformResultMetadata getResultMetadata() {
    return BOOLEAN_SV_NO_DICTIONARY_METADATA;
  }

  private boolean isCondSatisfied(String condFunc, double arg1, double arg2) {
    if (condFunc.equals("GreaterThan")) {
      return arg1 > arg2;
    }
    if (condFunc.equals("SmallerThan")){
      return arg1 < arg2;
    }
    throw new IllegalStateException("Unknown condFunc:" + condFunc);
  }

  @Override
  public int[] transformToIntValuesSV(ProjectionBlock projectionBlock) {

    int numDocs = projectionBlock.getNumDocs();
    if (_intValuesSV == null) {
      _intValuesSV = new int[numDocs];
    }

    Object leftJoinKeys = null;
    FieldSpec.DataType resultDataType = _joinValueFunctions.getResultMetadata().getDataType();
    switch (resultDataType.getStoredType()) {
        case INT:
          leftJoinKeys = _joinValueFunctions.transformToIntValuesSV(projectionBlock);
          break;
        case LONG:
          leftJoinKeys = _joinValueFunctions.transformToLongValuesSV(projectionBlock);
          break;
        case FLOAT:
          leftJoinKeys= _joinValueFunctions.transformToFloatValuesSV(projectionBlock);
          break;
        case DOUBLE:
          leftJoinKeys = _joinValueFunctions.transformToDoubleValuesSV(projectionBlock);
          break;
        case STRING:
          leftJoinKeys = _joinValueFunctions.transformToStringValuesSV(projectionBlock);
          break;
        case BYTES:
          leftJoinKeys = _joinValueFunctions.transformToBytesValuesSV(projectionBlock);
          break;
        default:
          throw new IllegalStateException("Unknown column type for primary key");
      }

    double[] condCol1 = new double[numDocs];
    // Get filter variable
    FieldSpec.DataType storedType = _condCol1.getResultMetadata().getDataType().getStoredType();
    System.out.println("condCol1: colm:" + ((IdentifierTransformFunction) _condCol1).getColumnName());
    switch (storedType) {
//      case INT:
//        condCol1 = _condCol1.transformToIntValuesSV(projectionBlock);
//        break;
//      case LONG:
//        condCol1 = _condCol1.transformToLongValuesSV(projectionBlock);
//        break;
//      case FLOAT:
//        condCol1 = _condCol1.transformToFloatValuesSV(projectionBlock);
//        break;
      case DOUBLE:
        condCol1 = _condCol1.transformToDoubleValuesSV(projectionBlock);
        break;
//      case STRING:
//        condCol1 = _condCol1.transformToStringValuesSV(projectionBlock);
//        break;
      default:
        throw new IllegalStateException("Unknown supported type for condCol1");
    }

    for (int i = 0; i < numDocs; i++) {
      // prepare joinKey
      Object[] joinKeyValue = new Object[1];
      if (leftJoinKeys instanceof int[]) {
        joinKeyValue[0] = ((int[]) leftJoinKeys)[i];
      } else if (leftJoinKeys instanceof long[]) {
        joinKeyValue[0]  = ((long[]) leftJoinKeys)[i];
      } else if (leftJoinKeys instanceof String[]) {
        joinKeyValue[0]  = ((String[]) leftJoinKeys)[i];
      } else if (leftJoinKeys instanceof float[]) {
        joinKeyValue[0]  = ((float[]) leftJoinKeys)[i];
      } else if (leftJoinKeys instanceof double[]) {
        joinKeyValue[0]  = ((double[]) leftJoinKeys)[i];
      } else if (leftJoinKeys instanceof byte[][]) {
        joinKeyValue[0]  = new ByteArray(((byte[][]) leftJoinKeys)[i]);
      }
      PrimaryKey key = new PrimaryKey(joinKeyValue);
      // lookup
      Object[] row = _keyValuesMap.getOrDefault(key, null);
      _intValuesSV[i] = 0;
      if (row != null) {
        Object condConl2 = row.getValue(_condCol2);
        System.out.println("liuyao primaryKey:" + primaryKey + " condCol1:" + condCol1[i] + "  condConl2:" + condConl2);

        if (isCondSatisfied(_filterFunc, condCol1[i], ((Number) condConl2).doubleValue())) {
          _intValuesSV[i] = 1;
        }
      }
    }
    return _intValuesSV;
  }
}