package repository.dao;

import janggi.piece.Team;
import repository.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AttackTurnDAO {

    private final Connection connection;

    public AttackTurnDAO(ConnectionManager manager) {
        this.connection = manager.getConnection();
    }

    public void saveTurn(Team team) {
        String query = """
                INSERT INTO attack_turn (team_name)
                VALUES (?)
                ON DUPLICATE KEY UPDATE team_name = VALUES(team_name)
                """;

        int TEAM_INDEX = 1;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(TEAM_INDEX, team.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("공격 턴 저장 실패", e);
        }
    }

    public Team loadAttackTeam() {
        String query = """
                SELECT team_name FROM attack_turn 
                """;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return Team.convert(resultSet.getString("team_name"));
            }
            throw new IllegalStateException("공격 턴 데이터 없음");
        } catch (SQLException e) {
            throw new RuntimeException("공격 턴 불러오기 실패", e);
        }
    }

    public void updateTurn(Team team) {
        String query = """
                UPDATE attack_turn SET team_name=(?)
                """;

        int TEAM_INDEX = 1;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(TEAM_INDEX, team.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("공격 턴 업데이트 실패", e);
        }
    }

    public void resetTurn() {
        String query = "DELETE FROM attack_turn";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("리셋 실패", e);
        }
    }
}
