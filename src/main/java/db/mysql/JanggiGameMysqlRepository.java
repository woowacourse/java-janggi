package db.mysql;

import db.JanggiGameRepository;
import domain.GameState;
import domain.Team;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JanggiGameMysqlRepository implements JanggiGameRepository {

    @Override
    public Team getTurn(final Connection connection) {

        final var getTurnSql = "select team from turn";

        try (final var turnStmt = connection.prepareStatement(getTurnSql);
             final ResultSet resultSet = turnStmt.executeQuery()) {

            if (resultSet.next()) {
                final String team = resultSet.getString("team");
                return Team.valueOf(team.toUpperCase());
            } else {
                throw new SQLException("턴 정보가 존재하지 않습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다." + e);
        }
    }

    @Override
    public GameState getGameState(final Connection connection) {

        final var gameStateSql = "select state from game_state where id = 1";

        try (final var gameState = connection.prepareStatement(gameStateSql);
             final ResultSet resultSet = gameState.executeQuery()) {

            if (resultSet.next()) {
                final String state = resultSet.getString("state");
                return GameState.valueOf(state);
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다." + e);
        }
        throw new IllegalStateException("데이터를 가져오는 과정에서 에러가 발생했습니다.");
    }

    @Override
    public void updateGameState(final Connection connection, final GameState gameState) {

        final var gameStateSql = "update game_state SET state = ? where id = 1";

        try (final var gameStateStmt = connection.prepareStatement(gameStateSql)) {
            gameStateStmt.setString(1, gameState.name());
            gameStateStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다." + e);
        }
    }

    @Override
    public void changeTurn(final Connection connection, final Team team) {

        final var updateTurnSql = "update turn set team = ?";

        try (final var turnStmt = connection.prepareStatement(updateTurnSql)) {
            turnStmt.setString(1, team.name());
            turnStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다.\"" + e);
        }
    }
}
