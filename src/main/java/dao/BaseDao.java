package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 오류: " + e.getMessage(), e);
        }
    }

    public <T> List<T> executeQuery(String query,
                                    SqlConsumer<PreparedStatement> parameterBinder,
                                    SqlFunction<ResultSet, T> RowMapper) {
        List<T> result = new ArrayList<>();

        return executeQuery(query, preparedStatement -> {
            parameterBinder.accept(preparedStatement);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(RowMapper.apply(resultSet));
                }
            }
            return result;
        });
    }

    public <T> T executeQuery(String query, SqlFunction<PreparedStatement, T> function) {
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            return function.apply(preparedStatement);

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 쿼리 실행 중 오류가 발생하였습니다.", e);
        }
    }

    public void executeUpdate(String query, SqlConsumer<PreparedStatement> consumer) {
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 업데이트 쿼리 실행 중 오류가 발생하였습니다.", e);
        }
    }

    @FunctionalInterface
    protected interface SqlFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    @FunctionalInterface
    protected interface SqlConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
