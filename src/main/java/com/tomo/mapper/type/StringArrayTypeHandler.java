package com.tomo.mapper.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringArrayTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setArray(i, ps.getConnection().createArrayOf("text", parameter.toArray()));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Array array = rs.getArray(columnName);
        if (array == null) {
            return new ArrayList<>();
        }
        return Arrays.asList((String[]) array.getArray());
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getArray(columnIndex) == null) {
            return new ArrayList<>();
        }
        return Arrays.asList((String[]) rs.getArray(columnIndex).getArray());
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getArray(columnIndex) == null) {
            return new ArrayList<>();
        }
        return Arrays.asList((String[]) cs.getArray(columnIndex).getArray());
    }
}