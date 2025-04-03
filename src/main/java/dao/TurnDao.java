package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {

    private final Connection connection;

    public TurnDao(final Connection connection) {
        this.connection = connection;
    }

    public void save(final TurnEntity turnEntity) {
        String sql = """
                INSERT INTO turn (value)
                VALUES (?);
                """;
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAll() {
        String sql = "DELETE FROM turn;";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
