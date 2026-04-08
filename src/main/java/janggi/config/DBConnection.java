package janggi.config;

import janggi.infrastructure.entity.EntityMapper;
import java.util.List;
import java.util.Optional;

public interface DBConnection {

    void init();

    <R> Optional<R> executeSelect(String sql, EntityMapper<R> entityMapper, Object... parameters);

    <R> List<R> executeSelectAll(String sql, EntityMapper<R> entityMapper, Object... parameters);

    long executeUpdate(String sql, Object... parameters);

    List<Long> executeBatchUpdate(String sql, List<Object[]> parametersList);

    boolean executeDelete(String sql, Object... parameters);

    void closeConnection();

}
