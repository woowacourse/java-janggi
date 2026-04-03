package janggi.config;

import janggi.global.EntityMapper;
import java.util.Optional;

public interface DBConnection {

    void init();

    <R> Optional<R> executeSelect(String sql, EntityMapper<R> entityMapper);

    long executeUpdate(String sql);

    boolean executeDelete(String sql);

    void closeConnection();

}
