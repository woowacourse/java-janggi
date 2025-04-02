package dao;

import db.DatabaseConnector;
import domain.piece.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDaoImpl implements TurnDao {

    private final DatabaseConnector databaseConnector;

    public TurnDaoImpl(final DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public Team load() {
        final String query = "SELECT turn FROM turn ORDER BY turn DESC LIMIT 1";
        try (final Connection connection = databaseConnector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String turn = resultSet.getString("turn");
                return Team.valueOf(turn);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 기록 조회 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }

    @Override
    public void save(final Connection connection, final Team turn) {
        final String query = "INSERT INTO turn (turn) VALUES(?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 기록 저장 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }

    @Override
    public void remove(final Connection connection) {
        final String query = "DELETE FROM turn";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 기록 삭제 중 오류가 발생했습니다. " + e.getMessage(), e);
        }
    }
}
