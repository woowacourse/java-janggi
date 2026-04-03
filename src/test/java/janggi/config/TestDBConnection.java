package janggi.config;

import janggi.global.EntityMapper;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Properties;

public final class TestDBConnection implements DBConnection {

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

    @Override
    public void init() {
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, id, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <R> Optional<R> executeSelect(final String sql, EntityMapper<R> mapper) {
        try (
            final Statement preparedStatement = connection.createStatement();
            final ResultSet resultSet = preparedStatement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return Optional.of(mapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public long executeUpdate(final String sql) {
        long generatedKey = -1;
        try (final Statement preparedStatement = connection.createStatement()) {
            preparedStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try (final ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedKey = generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedKey;
    }

    @Override
    public boolean executeDelete(final String sql) {
        int affectedRowCount = 0;
        try (
            final Connection connection = DriverManager.getConnection(url, id, password);
            final Statement preparedStatement = connection.createStatement();
        ) {
            affectedRowCount = preparedStatement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRowCount != 0;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
