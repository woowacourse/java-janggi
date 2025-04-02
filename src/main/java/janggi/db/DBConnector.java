package janggi.db;

import java.sql.Connection;

public interface DBConnector {

    Connection getConnection();
}
