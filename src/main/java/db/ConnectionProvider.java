package db;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection();
}
