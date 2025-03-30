package dao;

import game.Team;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardDao {

    public Team findCurrentTeam() {
        String query = "SELECT * FROM board WHERE board_id = 1";
        return BaseDao.executeQuery(query, this::mapResultSetToTeam);
    }

    public void resetCurrentTeam() {
        var query = "UPDATE board SET current_team_id = 1 WHERE board_id = 1";
        BaseDao.executeUpdate(query, preparedStatement -> {
        });
    }

    public void updateCurrentTeam(Team team) {
        var query = "UPDATE board SET current_team_id = ? WHERE board_id = 1";
        BaseDao.executeUpdate(query, preparedStatement ->
                preparedStatement.setInt(1, team.getId())
        );
    }

    private Team mapResultSetToTeam(PreparedStatement preparedStatement) throws SQLException {
        var resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int teamId = resultSet.getInt("current_team_id");
            return Team.findById(teamId);
        }
        throw new IllegalArgumentException("[ERROR] 보드가 유효하지 않습니다.");
    }
}
