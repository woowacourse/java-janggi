package persistence.connector;

import java.sql.Connection;

public interface DataBaseConnector {

    Connection getConnection();
}
