package util;

import java.sql.Connection;

public interface DatabaseConnector {

    public Connection getConnection();
}
