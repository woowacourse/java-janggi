package janggi.config;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalWorks<T> {

    T execute(Connection connection) throws SQLException;

}
