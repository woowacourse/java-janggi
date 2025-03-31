package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {

    private static final String IP = "localhost";
    private static final String PORT = "13306";
    private static final String DATABASE_NAME = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void save(final TurnEntity turnEntity) {
        String sql = """
                INSERT INTO turn (value)
                VALUES (?);
                """;
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, turnEntity.value());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(final TurnEntity turnEntity) {
        String sql = """
                UPDATE turn
                SET value = ? 
                """;
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, turnEntity.value());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TurnEntity find() {
        String sql = """
                SELECT *
                FROM turn
                LIMIT 1;
                """;
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new TurnEntity(resultSet.getLong(1), resultSet.getInt(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists() {
        String sql = """
                SELECT *
                FROM turn;
                """;
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAll() {
        String sql = "DELETE FROM turn;";
        try (
                final Connection connection = getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE_NAME + OPTION, USERNAME, PASSWORD
            );
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.");
        }
    }

}
