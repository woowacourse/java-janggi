package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager {
    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";

    private static final boolean OPT_AUTO_COMMIT = false;
    private static final int  OPT_VALID_TIMEOUT = 500;

    private Connection conn = null;
    private String driver;
    private String url;

    public SQLManager(String url) {
        this.driver = SQLITE_JDBC_DRIVER;
        this.url = url;
    }

    public Connection createConnection() {
        try {
            Class.forName(this.driver);
            this.conn = DriverManager.getConnection(this.url);
            this.conn.setAutoCommit(OPT_AUTO_COMMIT);
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return this.conn;
    }

    public void closeConnection() {
        try {
            if(this.conn == null) {
                return;
            }
            this.conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection ensureConnection() {
        try {
            if (this.conn == null || this.conn.isClosed() || !this.conn.isValid(OPT_VALID_TIMEOUT)) {
                closeConnection();
                createConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.conn;
    }

    public Connection getConnection() {
        return this.conn;
    }
}
