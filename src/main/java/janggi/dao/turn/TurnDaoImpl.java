package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.piece.players.Team;
import janggi.piece.players.Turn;
import janggi.utils.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class TurnDaoImpl implements TurnDao {

    private static final DBUtil dbUtil = DBUtil.getInstance();
    private static final TurnDao turnDao = new TurnDaoImpl();

    private TurnDaoImpl() {
    }

    public static TurnDao getTurnDao() {
        return turnDao;
    }

    @Override
    public void initialize(final TurnDto turnDto) {
        final var query = "INSERT INTO turn (current_team) VALUES(?)";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final int teamId = getTeamIdByName(connection, turnDto.team().name());
            preparedStatement.setInt(1, teamId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Turn selectCurrentTeam() {
        final var query = "SELECT current_team FROM turn LIMIT 1";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final String teamName = getTeamNameById(connection, resultSet.getInt("current_team"));
                return Turn.initialize(Team.from(teamName));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateTurn(final Team team) {
        final var updateQuery = "UPDATE turn SET current_team = ?";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, getTeamIdByName(connection, team.name()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final var deleteQuery = "DELETE FROM turn WHERE turn_id > 0";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTeamNameById(final Connection connection, final int teamId) {
        final var query = "SELECT name FROM team WHERE team_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] Team을 찾을 수 없습니다.");
    }

    private int getTeamIdByName(final Connection connection, final String teamName) {
        final var query = "SELECT * FROM team WHERE name = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teamName);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("team_id");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] Team을 찾을 수 없습니다.");
    }
}
