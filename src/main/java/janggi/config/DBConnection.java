package janggi.config;

import janggi.global.EntityMapper;
import java.util.List;
import java.util.Optional;

public interface DBConnection {

    void init();

    <R> Optional<R> executeSelect(String sql, EntityMapper<R> entityMapper);

    <R> List<R> executeSelectAll(String sql, EntityMapper<R> entityMapper);

    List<Long> executeUpdate(String sql);

    boolean executeDelete(String sql);

    void closeConnection();

}
