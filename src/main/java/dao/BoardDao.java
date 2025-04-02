package dao;

import game.Team;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardDao extends BaseDao {

    public Team findCurrentTeam() {
        var query = "SELECT * FROM board WHERE board_id = 1";
        return executeQuery(query, this::mapResultSetToTeam);
    }

    public void resetCurrentTeam() {
        var query = "UPDATE board SET current_team = green WHERE board_id = 1";
        executeUpdate(query, preparedStatement -> {
        });
    }

    public void updateCurrentTeam(Team team) {
        var query = "UPDATE board SET current_team = ? WHERE board_id = 1";
        executeUpdate(query, preparedStatement ->
                preparedStatement.setString(1, team.name().toLowerCase())
        );
    }

    private Team mapResultSetToTeam(PreparedStatement preparedStatement) throws SQLException {
        var resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String currentTeam = resultSet.getString("current_team");
            return Team.fromName(currentTeam);
        }
        throw new IllegalArgumentException("[ERROR] 보드가 유효하지 않습니다.");
    }
}
