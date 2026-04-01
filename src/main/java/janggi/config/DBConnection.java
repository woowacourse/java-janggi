package janggi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBConnection {

    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
    private static final String URL = "jdbc:h2:./test";
    private static final String ID = "sa";
    private static final String PASSWORD = "";

    private static Connection connection = null;
    private static Statement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void init() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(URL, ID, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
