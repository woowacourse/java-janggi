package janggi.config;

import janggi.global.EntityMapper;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class TestDBConnection {

    private static String driverClassName;
    private static String url;
    private static String id;
    private static String password;
    private static Connection connection;

    static {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Properties properties = new Properties();
        try (final InputStream inputStream = classLoader.getResourceAsStream(
            "application.properties")) {
            properties.load(inputStream);
            driverClassName = properties.getProperty("h2-db.driver-class-name");
            url = properties.getProperty("h2-db.datasource.url");
            id = properties.getProperty("h2-db.datasource.id");
            password = properties.getProperty("h2-db.datasource.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, id, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <R> R executeSelect(final String sql, EntityMapper<R> mapper) {
        R entity = null;
        try (
            final Statement preparedStatement = connection.createStatement();
            final ResultSet resultSet = preparedStatement.executeQuery(sql)) {

            entity = mapper.map(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public static int executeUpdate(final String sql) {
        int updateRecordCount = 0;
        Statement preparedStatement = null;
        try {
            preparedStatement = connection.createStatement();
            updateRecordCount = preparedStatement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
        }
        return updateRecordCount;
    }

    public static long executeInsert(final String sql) {
        long generatedKey = -1;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKey = generatedKeys.getLong(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedKey;
    }

    private static void closeConnection(final Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeStatement(final Statement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
