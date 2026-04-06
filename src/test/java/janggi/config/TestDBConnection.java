package janggi.config;

import janggi.dto.H2DBPropertiesDto;
import janggi.global.EntityMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TestDBConnection implements DBConnection {

    private final String driverClassName;
    private final String url;
    private final String id;
    private final String password;
    private Connection connection;

    public TestDBConnection(final H2DBPropertiesDto h2DBPropertiesDto) {
        driverClassName = h2DBPropertiesDto.driverClassName();
        url = h2DBPropertiesDto.url();
        id = h2DBPropertiesDto.id();
        password = h2DBPropertiesDto.password();
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
    public List<Long> executeSelectForIds(String sql, Object... parameters) {
        final List<Long> ids = new ArrayList<>();
        try (
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ids.add(resultSet.getLong("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }

    @Override
    public <R> Optional<R> executeSelect(final String sql, final EntityMapper<R> mapper,
        final Object... parameters) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public <R> List<R> executeSelectAll(final String sql, final EntityMapper<R> entityMapper,
        final Object... parameters) {
        final List<R> result = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(entityMapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public long executeUpdate(final String sql, Object... parameters) {
        long generatedKey = 0;
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql,
            Statement.RETURN_GENERATED_KEYS)) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }

            preparedStatement.executeUpdate();
            try (final ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedKey = resultSet.getLong(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedKey;
    }

    @Override
    public List<Long> executeBatchUpdate(String sql, List<Object[]> parametersList) {
        final List<Long> generatedKeys = new ArrayList<>();
        try (final PreparedStatement preparedStatement =
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            for (final Object[] parameters : parametersList) {
                for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                    preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            try (final ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    generatedKeys.add(resultSet.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedKeys;
    }

    @Override
    public boolean executeDelete(final String sql, final Object... parameters) {
        int affectedRowCount = 0;
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }
            affectedRowCount = preparedStatement.executeUpdate();
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
