package janggi.dao.turn;

import janggi.dao.BaseDao;
import janggi.domain.players.Team;
import janggi.domain.players.Turn;
import janggi.dto.TurnDto;
import janggi.utils.DBUtil;
import java.sql.SQLException;

public class TurnDaoImpl extends BaseDao implements TurnDao {

    private static final DBUtil dbUtil = DBUtil.getInstance();
    private static final TurnDao turnDao = new TurnDaoImpl();

    private TurnDaoImpl() {
    }

    public static TurnDao getTurnDao() {
        return turnDao;
    }

    @Override
    public void initialize(final TurnDto turnDto) {
        deleteAll();
        insertCurrentTeam(turnDto);
    }

    @Override
    public Turn selectCurrentTeam() {
        final var query = "SELECT current_team FROM turn LIMIT 1";
        try (var connection = dbUtil.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final String teamName = getTeamNameById(connection, resultSet.getInt("current_team"));
                return Turn.initialize(Team.from(teamName));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] 현재 턴 정보를 찾을 수 없습니다.");
    }

    @Override
    public void updateTurn(final Team team) {
        final var updateQuery = "UPDATE turn SET current_team = ?";
        try (var connection = dbUtil.getConnection();
             var preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, getTeamIdByName(connection, team.name()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        executeUpdate("DELETE FROM turn WHERE turn_id > 0");
    }

    private void insertCurrentTeam(final TurnDto turnDto) {
        final var query = "INSERT INTO turn (current_team) VALUES(?)";
        try (var connection = dbUtil.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            final int teamId = getTeamIdByName(connection, turnDto.team().name());
            preparedStatement.setInt(1, teamId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
