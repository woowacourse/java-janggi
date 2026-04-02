package janggi.config;

import janggi.global.EntityMapper;

public interface DBConnection {

    void init();

    <R> R executeSelect(String sql, EntityMapper<R> entityMapper);

    long executeUpdate(String sql);

    void closeConnection();

}
