package application.persistence;

import java.sql.Connection;

public interface DbConnector {

    Connection getConnection();
}
