package janggi.dao;

import janggi.DBConnection;

import janggi.entity.Game;
import java.sql.*;
import java.time.LocalDateTime;

public class GameDAO {

    public Game loadLatestGameOrNull() {
        String sql = "SELECT game_id, created_at FROM Game WHERE deleted_at IS NULL ORDER BY created_at DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return new Game(
                        rs.getInt("game_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Game createNewGame() {
        String sql = "INSERT INTO Game (created_at) VALUES (CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return new Game(
                        rs.getInt(1),
                        LocalDateTime.now(),
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void softDeleteGame(final int gameId) {
        String sql = "UPDATE Game SET deleted_at = CURRENT_TIMESTAMP WHERE game_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}