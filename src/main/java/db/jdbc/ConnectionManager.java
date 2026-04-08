package db.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public interface ConnectionManager {

    Connection getConnection() throws SQLException;

    DataSource getDataSource();
}
