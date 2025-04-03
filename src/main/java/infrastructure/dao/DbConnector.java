package infrastructure.dao;

import java.sql.Connection;

public interface DbConnector {

    Connection getConnection();
}
