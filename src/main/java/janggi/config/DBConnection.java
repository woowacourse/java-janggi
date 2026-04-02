package janggi.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public final class DBConnection {

    private static String DRIVER_CLASS_NAME;
    private static String URL;
    private static String ID;
    private static String PASSWORD;

    static {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Properties properties = new Properties();
        try (final InputStream inputStream = classLoader.getResourceAsStream(
            "application.properties")) {
            properties.load(inputStream);
            DRIVER_CLASS_NAME = properties.getProperty("h2-db.driver-class-name");
            URL = properties.getProperty("h2-db.datasource.dev-url");
            ID = properties.getProperty("h2-db.datasource.id");
            PASSWORD = properties.getProperty("h2-db.datasource.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeSelect(final String sql) {
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(URL, ID, PASSWORD);
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement, resultSet);
        }
        return resultSet;
    }

    public static int executeUpdate(final String sql) {
        int updateRecordCount = 0;
        Connection connection = null;
        Statement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, ID, PASSWORD);
            preparedStatement = connection.createStatement();
            updateRecordCount = preparedStatement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement);
        }
        return updateRecordCount;
    }

    public static long executeInsert(final String sql) {
        long generatedKey = -1;
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(URL, ID, PASSWORD);
            preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKey = generatedKeys.getLong(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement, resultSet);
        }

        return generatedKey;
    }

    private static void closeAll(final Connection connection, final Statement preparedStatement) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeAll(final Connection connection, final Statement preparedStatement,
        final ResultSet resultSet) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
