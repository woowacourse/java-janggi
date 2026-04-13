package janggi.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcDataSource {
    Connection getConnection() throws SQLException;
}
