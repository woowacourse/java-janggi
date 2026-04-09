package janggi.config;

import janggi.dto.H2DBPropertiesDto;
import janggi.exception.SQLExceptionHandler;
import janggi.infrastructure.entity.EntityMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StandardDBConnection implements DBConnection {

    private final String driverClassName;
    private final String url;
    private final String id;
    private final String password;

    public StandardDBConnection(final H2DBPropertiesDto h2DBPropertiesDto) {
        driverClassName = h2DBPropertiesDto.driverClassName();
        url = h2DBPropertiesDto.url();
        id = h2DBPropertiesDto.id();
        password = h2DBPropertiesDto.password();
    }

    @Override
    public void init() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(String.format(
                "드라이버 클래스 로드 실패, %s 드라이버 클래스를 찾을 수 없습니다", driverClassName));
        }
    }

    @Override
    public <R> Optional<R> executeSelect(final String sql, final EntityMapper<R> mapper,
        final Object... parameters) {
        try (
            final Connection connection = DriverManager.getConnection(url, id, password);
            final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e, "SELECT 쿼리에 실패했습니다.");
        }

        return Optional.empty();
    }

    @Override
    public <R> List<R> executeSelectAll(final String sql, final EntityMapper<R> entityMapper,
        final Object... parameters) {
        final List<R> result = new ArrayList<>();
        try (
            final Connection connection = DriverManager.getConnection(url, id, password);
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(entityMapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e, "SELECT 쿼리에 실패했습니다.");
        }

        return result;
    }

    @Override
    public long executeUpdate(final String sql, Object... parameters) {
        long generatedKey = 0;
        try (
            final Connection connection = DriverManager.getConnection(url, id, password);
            final PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)
        ) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }

            preparedStatement.executeUpdate();
            try (final ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedKey = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e, "INSERT/UPDATE 쿼리에 실패했습니다.");
        }

        return generatedKey;
    }

    @Override
    public List<Long> executeBatchUpdate(String sql, List<Object[]> parametersList) {
        final List<Long> generatedKeys = new ArrayList<>();
        try (
            final Connection connection = DriverManager.getConnection(url, id, password);
            final PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)
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
            SQLExceptionHandler.handle(e, "BATCH INSERT/UPDATE 쿼리에 실패했습니다.");
        }

        return generatedKeys;
    }

    @Override
    public boolean executeDelete(final String sql, final Object... parameters) {
        int affectedRowCount = 0;
        try (
            final Connection connection = DriverManager.getConnection(url, id, password);
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                preparedStatement.setObject(parameterIndex + 1, parameters[parameterIndex]);
            }
            affectedRowCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            SQLExceptionHandler.handle(e, "DELETE 쿼리에 실패했습니다.");
        }
        return affectedRowCount != 0;
    }

    @Override
    public void closeConnection() {

    }

}
