package janggi.infrastructure.dao;

import janggi.infrastructure.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDao {

    public void saveGameList(Connection connection, long gameId, String turn) throws SQLException {
        String sql = "MERGE INTO game_list (game_id, current_turn) KEY (game_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.setString(2, turn);
            pstmt.executeUpdate();
        }
    }

    public void deletePieceByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        }
    }

    public void insertPieces(Connection connection, long gameId, PieceDto dto) throws SQLException {
        String sql = "INSERT INTO piece (game_id, x, y, piece_type, team) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.setInt(2, dto.x());
            pstmt.setInt(3, dto.y());
            pstmt.setString(4, dto.pieceType());
            pstmt.setString(5, dto.team());
            pstmt.executeUpdate();
        }
    }

    public List<Long> findAllGameIds(Connection connection) throws SQLException {
        String sql = "SELECT game_id FROM game_list";
        List<Long> gameIds = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                gameIds.add(rs.getLong("game_id"));
            }
        }
        return gameIds;
    }

    public String findTurnByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "SELECT current_turn FROM game_list WHERE game_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("current_turn");
                }
            }
        }
        throw new IllegalArgumentException("해당 ID의 게임이 존재하지 않습니다.");
    }

    public List<PieceDto> findPiecesByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "SELECT x, y, piece_type, team FROM piece WHERE game_id = ?";
        List<PieceDto> pieces = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pieces.add(new PieceDto(
                            rs.getInt("x"),
                            rs.getInt("y"),
                            rs.getString("piece_type"),
                            rs.getString("team")
                    ));
                }
            }
        }
        return pieces;
    }

    public boolean existsById(Connection connection, long gameId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM game_list WHERE game_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
