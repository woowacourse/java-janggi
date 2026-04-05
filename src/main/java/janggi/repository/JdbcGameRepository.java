package janggi.repository;

import janggi.entity.GameEntity;
import janggi.exception.database.GameCreationException;
import janggi.exception.database.TurnUpdateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcGameRepository implements GameRepository{
    @Override
    public void save(Connection conn, GameEntity entity) {
        String sql = "INSERT INTO Game (game_id, state, turn, start_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, entity.getGameId());
            pstmt.setString(2, entity.getState());
            pstmt.setString(3, entity.getTurn());
            pstmt.setDate(4, entity.getStartDate());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new GameCreationException(e);
        }
    }

    @Override
    public void updateTurn(Connection conn, int gameId, String nextTurn) {
        String sql = "UPDATE Game SET turn = ? WHERE game_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nextTurn);
            pstmt.setInt(2, gameId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new TurnUpdateException(e);
        }
    }
}
