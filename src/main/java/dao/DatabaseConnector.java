package dao;

import java.sql.Connection;

public interface DatabaseConnector {
    Connection getConnection();
}
