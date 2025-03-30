package dao;

import java.sql.Connection;

public interface DataBaseConnector {

    Connection getConnection();
}
