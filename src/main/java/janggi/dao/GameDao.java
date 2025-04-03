package janggi.dao;

import janggi.GameState;
import janggi.dao.entity.GameEntity;
import janggi.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {

    private final JanggiDatabase janggiDatabase = new JanggiDatabase();

    public GameEntity findByStatus(final GameState gameStatus) {
        final var query = "SELECT * FROM game WHERE status = ?";

        try (final var connection = janggiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, gameStatus.name());

            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new GameEntity(
                        resultSet.getLong("id"),
                        Team.from(resultSet.getString("current_turn")),
                        GameState.valueOf(resultSet.getString("status")),
                        resultSet.getDouble("chu_score"),
                        resultSet.getDouble("han_score"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addGame(final Team currentTurn, final GameState gameState) {
        final String query = "INSERT INTO game (current_turn, status, chu_score, han_score) VALUES (?, ?, 72, 73.5)";

        try (final Connection conn = janggiDatabase.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, currentTurn.getDescription());
            stmt.setString(2, gameState.name());

            stmt.executeUpdate();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGameStatus(final Long gameId, final Team newTurnTeam, final double chuScore,
                                 final double hanScore) {
        final String query = "UPDATE game SET current_turn = ?, chu_score = ?, han_score = ? WHERE id = ?";

        try (final Connection conn = janggiDatabase.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newTurnTeam.getDescription());
            stmt.setDouble(2, chuScore);
            stmt.setDouble(3, hanScore);
            stmt.setLong(4, gameId);

            stmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGameBy(final Long gameId) {
        final String query = "DELETE FROM game WHERE id = ?";

        try (final Connection conn = janggiDatabase.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, gameId);
            stmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
