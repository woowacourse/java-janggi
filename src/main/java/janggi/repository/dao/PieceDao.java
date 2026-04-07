package janggi.repository.dao;

import janggi.domain.Location;
import janggi.repository.entity.PieceEntity;
import janggi.repository.util.TransactionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceDao {

    private final TransactionManager transactionManager;

    public PieceDao(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Long insert(PieceEntity pieceEntity) {
        Connection conn = transactionManager.getConnection();
        String sql = "INSERT INTO piece (game_id, type, side, row_idx, col_idx) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, pieceEntity.getGameId());
            pstmt.setString(2, pieceEntity.getType());
            pstmt.setString(3, pieceEntity.getSide());
            pstmt.setInt(4, pieceEntity.getRowIdx());
            pstmt.setInt(5, pieceEntity.getColIdx());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("기물 정보 생성 실패", e);
        }

        throw new IllegalStateException("생성된 기물 ID를 가져올 수 없습니다.");
    }

    public Optional<PieceEntity> findById(Long id) {
        Connection conn = transactionManager.getConnection();
        String sql = "SELECT id, game_id, type, side, row_idx, col_idx FROM piece WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new PieceEntity(
                            rs.getLong("id"),
                            rs.getLong("game_id"),
                            rs.getString("type"),
                            rs.getString("side"),
                            rs.getInt("row_idx"),
                            rs.getInt("col_idx")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 조회 실패", e);
        }
    }

    public Optional<PieceEntity> findByGameIdAndLocation(Long gameId, Location location) {
        Connection conn = transactionManager.getConnection();
        String sql = "SELECT id, game_id, type, side, row_idx, col_idx FROM piece WHERE game_id = ? AND row_idx = ? AND col_idx = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.setLong(2, location.row());
            pstmt.setLong(3, location.col());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new PieceEntity(
                            rs.getLong("id"),
                            rs.getLong("game_id"),
                            rs.getString("type"),
                            rs.getString("side"),
                            rs.getInt("row_idx"),
                            rs.getInt("col_idx")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 조회 실패", e);
        }
    }

    public List<PieceEntity> findByGameId(Long gameId) {
        Connection conn = transactionManager.getConnection();
        String sql = "SELECT id, game_id, type, side, row_idx, col_idx FROM piece WHERE game_id = ?";
        List<PieceEntity> pieceEntities = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()
            ) {
                while (rs.next()) {
                    pieceEntities.add(new PieceEntity(
                            rs.getLong("id"),
                            rs.getLong("game_id"),
                            rs.getString("type"),
                            rs.getString("side"),
                            rs.getInt("row_idx"),
                            rs.getInt("col_idx")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("기물 목록 조회 실패", e);
        }
        return pieceEntities;
    }

    public void updatePosition(Long id, Location to) {
        Connection conn = transactionManager.getConnection();
        String sql = "UPDATE piece SET row_idx = ?, col_idx = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, to.row());
            pstmt.setInt(2, to.col());
            pstmt.setLong(3, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 정보 업데이트 실패", e);
        }
    }

    public void deleteById(Long id) {
        Connection conn = transactionManager.getConnection();
        String sql = "DELETE piece WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제 실패", e);
        }
    }
}
