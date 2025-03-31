package utils;

import config.DBConfig;
import infra.dao.exception.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class DBUtils {

    private DBUtils() {
    }

    public static Long executeInsert(
        final String query,
        final Object... params
    ) {
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(
                 query, Statement.RETURN_GENERATED_KEYS)) {

            setParams(preparedStatement, params);
            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                throw new SQLException("생성된 키가 존재하지 않습니다.");
            }
        } catch (final SQLException e) {
            throw new DatabaseException("DB 작업 중 오류 발생. 쿼리: " + query, e);
        }
    }


    public static void executeUpdate(
        final String query,
        final Object... params
    ) {
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setParams(preparedStatement, params);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseException("DB 작업 중 오류 발생. 쿼리: " + query, e);
        }
    }

    public static <T> T executeQuery(
        final String query,
        final ResultSetMapper<T> resultSetMapper,
        final Object... params
    ) {
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setParams(preparedStatement, params);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSetMapper.map(resultSet);
            }
        } catch (final SQLException e) {
            throw new DatabaseException("DB 작업 중 오류 발생. 쿼리: " + query, e);
        }

        return null;
    }

    public static <T> List<T> executeQueryList(
        final String query,
        final ResultSetMapper<T> resultSetMapper,
        final Object... params
    ) {
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setParams(preparedStatement, params);
            final ResultSet resultSet = preparedStatement.executeQuery();

            final List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSetMapper.map(resultSet));
            }

            return result;
        } catch (final SQLException e) {
            throw new DatabaseException("DB 작업 중 오류 발생. 쿼리: " + query, e);
        }
    }

    private static void setParams(
        final PreparedStatement preparedStatement,
        final Object... params
    ) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            final int index = i + 1;
            final Object param = params[i];

            preparedStatement.setObject(index, param);
        }
    }

    public interface ResultSetMapper<T> {

        T map(final ResultSet resultSet) throws SQLException;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DBConfig.getUrl(),
            DBConfig.getUsername(),
            DBConfig.getPassword()
        );
    }
}
