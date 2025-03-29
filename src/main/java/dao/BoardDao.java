package dao;

import game.Team;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BoardDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("[ERROR] DB 연결 오류: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Team findCurrentTeam() {
        String query = "SELECT * FROM board WHERE board_id = 1";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int teamId = resultSet.getInt("current_team_id");
                    return Team.findById(teamId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생하였습니다.", e);
        }
        throw new IllegalArgumentException("[ERROR] 보드가 유효하지 않습니다.");
    }

    public void resetCurrentTeam() {
        var query = "UPDATE board SET current_team_id = 1 WHERE board_id = 1";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCurrentTeam(Team team) {
        var query = "UPDATE board SET current_team_id = ? WHERE board_id = 1";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, team.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
