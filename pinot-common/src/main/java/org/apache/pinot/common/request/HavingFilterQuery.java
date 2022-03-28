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
/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.pinot.common.request;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * AUTO GENERATED: DO NOT EDIT
 * Having Filter query
 *
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)", date = "2022-03-28")
public class HavingFilterQuery implements org.apache.thrift.TBase<HavingFilterQuery, HavingFilterQuery._Fields>, java.io.Serializable, Cloneable, Comparable<HavingFilterQuery> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HavingFilterQuery");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField AGGREGATION_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("aggregationInfo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("value", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField OPERATOR_FIELD_DESC = new org.apache.thrift.protocol.TField("operator", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField NESTED_FILTER_QUERY_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("nestedFilterQueryIds", org.apache.thrift.protocol.TType.LIST, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HavingFilterQueryStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HavingFilterQueryTupleSchemeFactory();

  private int id; // required
  private @org.apache.thrift.annotation.Nullable AggregationInfo aggregationInfo; // optional
  private @org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> value; // required
  private @org.apache.thrift.annotation.Nullable FilterOperator operator; // optional
  private @org.apache.thrift.annotation.Nullable java.util.List<java.lang.Integer> nestedFilterQueryIds; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    /**
     * This should be unique within a single request *
     */
    AGGREGATION_INFO((short)2, "aggregationInfo"),
    VALUE((short)3, "value"),
    /**
     *
     * @see FilterOperator
     */
    OPERATOR((short)4, "operator"),
    NESTED_FILTER_QUERY_IDS((short)5, "nestedFilterQueryIds");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // AGGREGATION_INFO
          return AGGREGATION_INFO;
        case 3: // VALUE
          return VALUE;
        case 4: // OPERATOR
          return OPERATOR;
        case 5: // NESTED_FILTER_QUERY_IDS
          return NESTED_FILTER_QUERY_IDS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.AGGREGATION_INFO,_Fields.OPERATOR};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.AGGREGATION_INFO, new org.apache.thrift.meta_data.FieldMetaData("aggregationInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT, "AggregationInfo")));
    tmpMap.put(_Fields.VALUE, new org.apache.thrift.meta_data.FieldMetaData("value", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.OPERATOR, new org.apache.thrift.meta_data.FieldMetaData("operator", org.apache.thrift.TFieldRequirementType.OPTIONAL,
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, FilterOperator.class)));
    tmpMap.put(_Fields.NESTED_FILTER_QUERY_IDS, new org.apache.thrift.meta_data.FieldMetaData("nestedFilterQueryIds", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HavingFilterQuery.class, metaDataMap);
  }

  public HavingFilterQuery() {
  }

  public HavingFilterQuery(
    int id,
    java.util.List<java.lang.String> value,
    java.util.List<java.lang.Integer> nestedFilterQueryIds)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.value = value;
    this.nestedFilterQueryIds = nestedFilterQueryIds;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HavingFilterQuery(HavingFilterQuery other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetAggregationInfo()) {
      this.aggregationInfo = new AggregationInfo(other.aggregationInfo);
    }
    if (other.isSetValue()) {
      java.util.List<java.lang.String> __this__value = new java.util.ArrayList<java.lang.String>(other.value);
      this.value = __this__value;
    }
    if (other.isSetOperator()) {
      this.operator = other.operator;
    }
    if (other.isSetNestedFilterQueryIds()) {
      java.util.List<java.lang.Integer> __this__nestedFilterQueryIds = new java.util.ArrayList<java.lang.Integer>(other.nestedFilterQueryIds);
      this.nestedFilterQueryIds = __this__nestedFilterQueryIds;
    }
  }

  public HavingFilterQuery deepCopy() {
    return new HavingFilterQuery(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.aggregationInfo = null;
    this.value = null;
    this.operator = null;
    this.nestedFilterQueryIds = null;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
    setIdIsSet(true);
  }

  public void unsetId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  /**
   * This should be unique within a single request *
   */
  @org.apache.thrift.annotation.Nullable
  public AggregationInfo getAggregationInfo() {
    return this.aggregationInfo;
  }

  /**
   * This should be unique within a single request *
   */
  public void setAggregationInfo(@org.apache.thrift.annotation.Nullable AggregationInfo aggregationInfo) {
    this.aggregationInfo = aggregationInfo;
  }

  public void unsetAggregationInfo() {
    this.aggregationInfo = null;
  }

  /** Returns true if field aggregationInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetAggregationInfo() {
    return this.aggregationInfo != null;
  }

  public void setAggregationInfoIsSet(boolean value) {
    if (!value) {
      this.aggregationInfo = null;
    }
  }

  public int getValueSize() {
    return (this.value == null) ? 0 : this.value.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.String> getValueIterator() {
    return (this.value == null) ? null : this.value.iterator();
  }

  public void addToValue(java.lang.String elem) {
    if (this.value == null) {
      this.value = new java.util.ArrayList<java.lang.String>();
    }
    this.value.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.String> getValue() {
    return this.value;
  }

  public void setValue(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> value) {
    this.value = value;
  }

  public void unsetValue() {
    this.value = null;
  }

  /** Returns true if field value is set (has been assigned a value) and false otherwise */
  public boolean isSetValue() {
    return this.value != null;
  }

  public void setValueIsSet(boolean value) {
    if (!value) {
      this.value = null;
    }
  }

  /**
   *
   * @see FilterOperator
   */
  @org.apache.thrift.annotation.Nullable
  public FilterOperator getOperator() {
    return this.operator;
  }

  /**
   *
   * @see FilterOperator
   */
  public void setOperator(@org.apache.thrift.annotation.Nullable FilterOperator operator) {
    this.operator = operator;
  }

  public void unsetOperator() {
    this.operator = null;
  }

  /** Returns true if field operator is set (has been assigned a value) and false otherwise */
  public boolean isSetOperator() {
    return this.operator != null;
  }

  public void setOperatorIsSet(boolean value) {
    if (!value) {
      this.operator = null;
    }
  }

  public int getNestedFilterQueryIdsSize() {
    return (this.nestedFilterQueryIds == null) ? 0 : this.nestedFilterQueryIds.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.Integer> getNestedFilterQueryIdsIterator() {
    return (this.nestedFilterQueryIds == null) ? null : this.nestedFilterQueryIds.iterator();
  }

  public void addToNestedFilterQueryIds(int elem) {
    if (this.nestedFilterQueryIds == null) {
      this.nestedFilterQueryIds = new java.util.ArrayList<java.lang.Integer>();
    }
    this.nestedFilterQueryIds.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.Integer> getNestedFilterQueryIds() {
    return this.nestedFilterQueryIds;
  }

  public void setNestedFilterQueryIds(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.Integer> nestedFilterQueryIds) {
    this.nestedFilterQueryIds = nestedFilterQueryIds;
  }

  public void unsetNestedFilterQueryIds() {
    this.nestedFilterQueryIds = null;
  }

  /** Returns true if field nestedFilterQueryIds is set (has been assigned a value) and false otherwise */
  public boolean isSetNestedFilterQueryIds() {
    return this.nestedFilterQueryIds != null;
  }

  public void setNestedFilterQueryIdsIsSet(boolean value) {
    if (!value) {
      this.nestedFilterQueryIds = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((java.lang.Integer)value);
      }
      break;

    case AGGREGATION_INFO:
      if (value == null) {
        unsetAggregationInfo();
      } else {
        setAggregationInfo((AggregationInfo)value);
      }
      break;

    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        setValue((java.util.List<java.lang.String>)value);
      }
      break;

    case OPERATOR:
      if (value == null) {
        unsetOperator();
      } else {
        setOperator((FilterOperator)value);
      }
      break;

    case NESTED_FILTER_QUERY_IDS:
      if (value == null) {
        unsetNestedFilterQueryIds();
      } else {
        setNestedFilterQueryIds((java.util.List<java.lang.Integer>)value);
      }
      break;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case AGGREGATION_INFO:
      return getAggregationInfo();

    case VALUE:
      return getValue();

    case OPERATOR:
      return getOperator();

    case NESTED_FILTER_QUERY_IDS:
      return getNestedFilterQueryIds();
    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case AGGREGATION_INFO:
      return isSetAggregationInfo();
    case VALUE:
      return isSetValue();
    case OPERATOR:
      return isSetOperator();
    case NESTED_FILTER_QUERY_IDS:
      return isSetNestedFilterQueryIds();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof HavingFilterQuery)
      return this.equals((HavingFilterQuery)that);
    return false;
  }

  public boolean equals(HavingFilterQuery that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_aggregationInfo = true && this.isSetAggregationInfo();
    boolean that_present_aggregationInfo = true && that.isSetAggregationInfo();
    if (this_present_aggregationInfo || that_present_aggregationInfo) {
      if (!(this_present_aggregationInfo && that_present_aggregationInfo))
        return false;
      if (!this.aggregationInfo.equals(that.aggregationInfo))
        return false;
    }

    boolean this_present_value = true && this.isSetValue();
    boolean that_present_value = true && that.isSetValue();
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (!this.value.equals(that.value))
        return false;
    }

    boolean this_present_operator = true && this.isSetOperator();
    boolean that_present_operator = true && that.isSetOperator();
    if (this_present_operator || that_present_operator) {
      if (!(this_present_operator && that_present_operator))
        return false;
      if (!this.operator.equals(that.operator))
        return false;
    }

    boolean this_present_nestedFilterQueryIds = true && this.isSetNestedFilterQueryIds();
    boolean that_present_nestedFilterQueryIds = true && that.isSetNestedFilterQueryIds();
    if (this_present_nestedFilterQueryIds || that_present_nestedFilterQueryIds) {
      if (!(this_present_nestedFilterQueryIds && that_present_nestedFilterQueryIds))
        return false;
      if (!this.nestedFilterQueryIds.equals(that.nestedFilterQueryIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + id;

    hashCode = hashCode * 8191 + ((isSetAggregationInfo()) ? 131071 : 524287);
    if (isSetAggregationInfo())
      hashCode = hashCode * 8191 + aggregationInfo.hashCode();

    hashCode = hashCode * 8191 + ((isSetValue()) ? 131071 : 524287);
    if (isSetValue())
      hashCode = hashCode * 8191 + value.hashCode();

    hashCode = hashCode * 8191 + ((isSetOperator()) ? 131071 : 524287);
    if (isSetOperator())
      hashCode = hashCode * 8191 + operator.getValue();

    hashCode = hashCode * 8191 + ((isSetNestedFilterQueryIds()) ? 131071 : 524287);
    if (isSetNestedFilterQueryIds())
      hashCode = hashCode * 8191 + nestedFilterQueryIds.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(HavingFilterQuery other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAggregationInfo()).compareTo(other.isSetAggregationInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAggregationInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.aggregationInfo, other.aggregationInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetValue()).compareTo(other.isSetValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.value, other.value);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOperator()).compareTo(other.isSetOperator());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperator()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operator, other.operator);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetNestedFilterQueryIds()).compareTo(other.isSetNestedFilterQueryIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNestedFilterQueryIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nestedFilterQueryIds, other.nestedFilterQueryIds);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("HavingFilterQuery(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (isSetAggregationInfo()) {
      if (!first) sb.append(", ");
      sb.append("aggregationInfo:");
      if (this.aggregationInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.aggregationInfo);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("value:");
    if (this.value == null) {
      sb.append("null");
    } else {
      sb.append(this.value);
    }
    first = false;
    if (isSetOperator()) {
      if (!first) sb.append(", ");
      sb.append("operator:");
      if (this.operator == null) {
        sb.append("null");
      } else {
        sb.append(this.operator);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("nestedFilterQueryIds:");
    if (this.nestedFilterQueryIds == null) {
      sb.append("null");
    } else {
      sb.append(this.nestedFilterQueryIds);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetId()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'id' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HavingFilterQueryStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HavingFilterQueryStandardScheme getScheme() {
      return new HavingFilterQueryStandardScheme();
    }
  }

  private static class HavingFilterQueryStandardScheme extends org.apache.thrift.scheme.StandardScheme<HavingFilterQuery> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HavingFilterQuery struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // AGGREGATION_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.aggregationInfo = new AggregationInfo();
              struct.aggregationInfo.read(iprot);
              struct.setAggregationInfoIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list26 = iprot.readListBegin();
                struct.value = new java.util.ArrayList<java.lang.String>(_list26.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _elem27;
                for (int _i28 = 0; _i28 < _list26.size; ++_i28)
                {
                  _elem27 = iprot.readString();
                  struct.value.add(_elem27);
                }
                iprot.readListEnd();
              }
              struct.setValueIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // OPERATOR
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.operator = org.apache.pinot.common.request.FilterOperator.findByValue(iprot.readI32());
              struct.setOperatorIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // NESTED_FILTER_QUERY_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list29 = iprot.readListBegin();
                struct.nestedFilterQueryIds = new java.util.ArrayList<java.lang.Integer>(_list29.size);
                int _elem30;
                for (int _i31 = 0; _i31 < _list29.size; ++_i31)
                {
                  _elem30 = iprot.readI32();
                  struct.nestedFilterQueryIds.add(_elem30);
                }
                iprot.readListEnd();
              }
              struct.setNestedFilterQueryIdsIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, HavingFilterQuery struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      if (struct.aggregationInfo != null) {
        if (struct.isSetAggregationInfo()) {
          oprot.writeFieldBegin(AGGREGATION_INFO_FIELD_DESC);
          struct.aggregationInfo.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.value != null) {
        oprot.writeFieldBegin(VALUE_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.value.size()));
          for (java.lang.String _iter32 : struct.value)
          {
            oprot.writeString(_iter32);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.operator != null) {
        if (struct.isSetOperator()) {
          oprot.writeFieldBegin(OPERATOR_FIELD_DESC);
          oprot.writeI32(struct.operator.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.nestedFilterQueryIds != null) {
        oprot.writeFieldBegin(NESTED_FILTER_QUERY_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.nestedFilterQueryIds.size()));
          for (int _iter33 : struct.nestedFilterQueryIds)
          {
            oprot.writeI32(_iter33);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }
  }

  private static class HavingFilterQueryTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HavingFilterQueryTupleScheme getScheme() {
      return new HavingFilterQueryTupleScheme();
    }
  }

  private static class HavingFilterQueryTupleScheme extends org.apache.thrift.scheme.TupleScheme<HavingFilterQuery> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HavingFilterQuery struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.id);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetAggregationInfo()) {
        optionals.set(0);
      }
      if (struct.isSetValue()) {
        optionals.set(1);
      }
      if (struct.isSetOperator()) {
        optionals.set(2);
      }
      if (struct.isSetNestedFilterQueryIds()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetAggregationInfo()) {
        struct.aggregationInfo.write(oprot);
      }
      if (struct.isSetValue()) {
        {
          oprot.writeI32(struct.value.size());
          for (java.lang.String _iter34 : struct.value)
          {
            oprot.writeString(_iter34);
          }
        }
      }
      if (struct.isSetOperator()) {
        oprot.writeI32(struct.operator.getValue());
      }
      if (struct.isSetNestedFilterQueryIds()) {
        {
          oprot.writeI32(struct.nestedFilterQueryIds.size());
          for (int _iter35 : struct.nestedFilterQueryIds)
          {
            oprot.writeI32(_iter35);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HavingFilterQuery struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.id = iprot.readI32();
      struct.setIdIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.aggregationInfo = new AggregationInfo();
        struct.aggregationInfo.read(iprot);
        struct.setAggregationInfoIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list36 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.value = new java.util.ArrayList<java.lang.String>(_list36.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _elem37;
          for (int _i38 = 0; _i38 < _list36.size; ++_i38)
          {
            _elem37 = iprot.readString();
            struct.value.add(_elem37);
          }
        }
        struct.setValueIsSet(true);
      }
      if (incoming.get(2)) {
        struct.operator = org.apache.pinot.common.request.FilterOperator.findByValue(iprot.readI32());
        struct.setOperatorIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list39 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.nestedFilterQueryIds = new java.util.ArrayList<java.lang.Integer>(_list39.size);
          int _elem40;
          for (int _i41 = 0; _i41 < _list39.size; ++_i41)
          {
            _elem40 = iprot.readI32();
            struct.nestedFilterQueryIds.add(_elem40);
          }
        }
        struct.setNestedFilterQueryIdsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
