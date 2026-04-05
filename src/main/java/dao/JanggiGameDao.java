package dao;

import database.MysqlConnectionManager;
import dto.GameStatus;
import dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.board.Country;
import model.pieces.PieceType;

public class JanggiGameDao {
    private final MysqlConnectionManager manager;

    public JanggiGameDao(MysqlConnectionManager manager) {
        this.manager = manager;
    }

    public int createGame(Country turn) {
        String sql = "INSERT INTO janggi_game (turn) VALUES (?)";
        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, turn.name());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void saveGame(int gameId, Country turn, List<PieceDto> pieces) {
        String updateTurnSql = "UPDATE janggi_game SET turn = ? WHERE id = ?";
        String deletePiecesSql = "DELETE FROM piece WHERE janggi_game_id = ?";
        String insertPieceSql = "INSERT INTO piece (janggi_game_id, row_pos, col_pos, country, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = manager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(updateTurnSql)) {
                pstmt.setString(1, turn.name());
                pstmt.setInt(2, gameId);
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(deletePiecesSql)) {
                pstmt.setInt(1, gameId);
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertPieceSql)) {
                for (PieceDto piece : pieces) {
                    pstmt.setInt(1, gameId);
                    pstmt.setInt(2, piece.row());
                    pstmt.setInt(3, piece.column());
                    pstmt.setString(4, piece.country().name());
                    pstmt.setString(5, piece.pieceType().name());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GameStatus findLatestStatus() {
        String sql = "SELECT id, turn FROM janggi_game ORDER BY id DESC LIMIT 1";
        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new GameStatus(
                        rs.getInt("id"),
                        Country.fromCountry(rs.getString("turn"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PieceDto> loadPiecesByGameId(int gameId) {
        List<PieceDto> pieces = new ArrayList<>();
        String sql = "SELECT * FROM piece WHERE janggi_game_id = ?";

        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pieces.add(new PieceDto(
                        rs.getInt("row_pos"),
                        rs.getInt("col_pos"),
                        Country.fromCountry(rs.getString("country")),
                        PieceType.fromType(rs.getString("type"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pieces;
    }
}
