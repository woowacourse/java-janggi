package janggi.database;

import java.sql.Connection;

public interface DBConnector {
    Connection getConnection();
}
