package janggi.infra.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {
    public static void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        Connection txConnection = ConnectionHolder.get();
        if (connection != txConnection) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
