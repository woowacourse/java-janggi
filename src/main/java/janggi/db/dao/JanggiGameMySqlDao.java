package janggi.db.dao;

import janggi.db.entity.JanggiGameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JanggiGameMySqlDao implements JanggiGameDao {

    public int selectOngoingGameCount(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM janggi_game";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public JanggiGameEntity selectOngoingGame(Connection connection) throws SQLException {
        String sql = "SELECT game_id, playing_side FROM janggi_game "
                + "ORDER BY game_id DESC LIMIT 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            int gameId = rs.getInt("game_id");
            String playingSide = rs.getString("playing_side");
            return new JanggiGameEntity(gameId, playingSide);
        }
    }

    @Override
    public int insertNewGame(Connection connection, String currentSide) throws SQLException {
        String sql = "INSERT INTO janggi_game (playing_side) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, currentSide);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    @Override
    public void updatePlayingSideByGameId(Connection connection, int gameId, String currentTurn) throws SQLException {
        String sql = "UPDATE janggi_game SET playing_side = ? WHERE game_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, currentTurn);
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteGameByGameId(Connection connection, int gameId) throws SQLException {
        String sql = "DELETE FROM janggi_game WHERE game_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();
        }
    }
}
