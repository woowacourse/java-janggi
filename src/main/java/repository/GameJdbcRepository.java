package repository;

import dto.GameDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameJdbcRepository implements GameRepository {

    private static final String ID = "id";
    private static final String CURRENT_TURN = "current_turn";

    @Override
    public void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS game (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    is_finished BOOLEAN NOT NULL DEFAULT FALSE,
                    current_turn TEXT NOT NULL DEFAULT 'CHO'
                )
                """);
    }

    @Override
    public boolean existsGame(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM game WHERE is_finished = FALSE");
        ResultSet rs = stmt.executeQuery();
        return rs.getInt(1) > 0;
    }

    public Optional<GameDto> findOngoingGame(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT id, current_turn FROM game WHERE is_finished = FALSE ORDER BY id DESC LIMIT 1");
        ResultSet rs = stmt.executeQuery();
        return Optional.of(new GameDto(rs.getInt(ID), rs.getString(CURRENT_TURN)));
    }

    @Override
    public int save(Connection connection, String turn) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO game (current_turn) VALUES (?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, turn);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        return rs.getInt(1);
    }

    @Override
    public void updateTurn(Connection connection, int gameId, String turn) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "UPDATE game SET current_turn = ? WHERE id = ?");
        stmt.setString(1, turn);
        stmt.setInt(2, gameId);
        stmt.executeUpdate();
    }

    @Override
    public void gameEnd(Connection connection, int gameId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "UPDATE game SET is_finished = ? WHERE id = ?");
        stmt.setBoolean(1, true);
        stmt.setInt(2, gameId);
        stmt.executeUpdate();
    }
}
