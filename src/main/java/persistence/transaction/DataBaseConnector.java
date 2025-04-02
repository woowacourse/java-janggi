package persistence.transaction;

import java.sql.Connection;

public interface DataBaseConnector {

    Connection getConnection();
}
