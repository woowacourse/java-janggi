package janggi.dao;

import janggi.util.PropertiesUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    private static final String SERVER = PropertiesUtil.get("db.server");
    private static final String DATABASE = PropertiesUtil.get("db.database");
    private static final String OPTION = PropertiesUtil.get("db.option");
    private static final String USERNAME = PropertiesUtil.get("db.username");
    private static final String PASSWORD = PropertiesUtil.get("db.password");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            throw new DatabaseSQLException(e);
        }
    }
}
