package janggi.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryProcessor {

    private static final DatabaseConnection databaseConnection = MySQLDatabaseConnection.getInstance();

    public static long executeInsert(final String query, final Object... parameters) {
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {

            setParameters(preparedStatement, parameters);
            preparedStatement.executeUpdate();
            try (final ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
            throw new SQLException("키 생성에 실패했습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스 작업 중 오류 발생: " + query, e);
        }
    }

    public static void executeUpdate(final String query, final Object... parameters) {
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setParameters(preparedStatement, parameters);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스 작업 중 오류 발생: " + query, e);
        }
    }

    public static <T> T executeQuery(final String query, final ResultSetMapper<T> resultSetMapper,
                                     final Object... params) {
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setParameters(preparedStatement, params);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetMapper.map(resultSet);
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스 작업 중 오류 발생: " + query, e);
        }

        return null;
    }

    public static <T> T executeQuery(final String query, final ResultSetMapper<T> resultSetMapper) {
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetMapper.map(resultSet);
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스 작업 중 오류 발생: " + query, e);
        }

        return null;
    }


    public static <T> List<T> executeQueryList(final String query, final ResultSetMapper<T> resultSetMapper,
                                               final Object... params) {
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setParameters(preparedStatement, params);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                final List<T> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(resultSetMapper.map(resultSet));
                }
                return result;
            }
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스 작업 중 오류 발생: " + query, e);
        }
    }

    public interface ResultSetMapper<T> {
        T map(final ResultSet resultSet) throws SQLException;
    }

    private static void setParameters(final PreparedStatement preparedStatement, final Object... params)
            throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }
}
