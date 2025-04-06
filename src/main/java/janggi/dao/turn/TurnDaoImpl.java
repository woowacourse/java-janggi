package janggi.dao.turn;

import janggi.dao.BaseDao;
import janggi.domain.players.Team;
import janggi.domain.players.Turn;
import janggi.dto.TurnDto;
import janggi.infrastructure.ProductionDatabaseProvider;
import janggi.infrastructure.DatabaseConnectionProvider;

public class TurnDaoImpl extends BaseDao implements TurnDao {

    private static final TurnDao turnDao = new TurnDaoImpl(ProductionDatabaseProvider.getInstance());

    public TurnDaoImpl(final DatabaseConnectionProvider databaseConnectionProvider) {
        super(databaseConnectionProvider);
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
        return executeQuery(query, (resultSet, connection) -> {
            final String teamName = getTeamNameById(connection, resultSet.getInt("current_team"));
            return Turn.initialize(Team.from(teamName));
        });
    }

    @Override
    public void updateTurn(final Team team) {
        final var query = "UPDATE turn SET current_team = ?";
        executeUpdate(query, (preparedStatement, connection) -> {
            preparedStatement.setInt(1, getTeamIdByName(connection, team.name()));
        });
    }

    @Override
    public void deleteAll() {
        executeUpdate("DELETE FROM turn WHERE turn_id > 0");
    }

    private void insertCurrentTeam(final TurnDto turnDto) {
        final var query = "INSERT INTO turn (current_team) VALUES(?)";
        executeUpdate(query, (preparedStatement, connection) -> {
            final int teamId = getTeamIdByName(connection, turnDto.team().name());
            preparedStatement.setInt(1, teamId);
        });
    }
}
