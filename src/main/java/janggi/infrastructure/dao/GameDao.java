package janggi.infrastructure.dao;

import janggi.infrastructure.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDao {

    public void saveGameList(Connection connection, long gameId, String turn) throws SQLException {
        String sql = "INSERT INTO GAME_LIST (GAME_ID, CURRENT_TURN) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.setString(2, turn);
            pstmt.executeUpdate();
        }
    }

    public void deletePieceByGameId(Connection conn, long gameId) throws SQLException {
        String sql = "DELETE FROM PIECE WHERE GAME_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        }
    }

    public void insertPieces(Connection conn, long gameId, PieceDto dto) throws SQLException {
        String sql = "INSERT INTO PIECE (GAME_ID, X, Y, PIECE_TYPE, TEAM) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.setInt(2, dto.x());
            pstmt.setInt(3, dto.y());
            pstmt.setString(4, dto.pieceType());
            pstmt.setString(5, dto.team());
            pstmt.addBatch();
            pstmt.executeBatch();
        }
    }
}