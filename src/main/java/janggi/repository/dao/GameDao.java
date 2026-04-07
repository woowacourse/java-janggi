package janggi.repository.dao;

import janggi.repository.entity.Game;
import janggi.repository.util.TransactionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDao {

    private final TransactionManager transactionManager;

    public GameDao(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Long insert(Game game) {
        Connection conn = transactionManager.getConnection();
        String sql = "INSERT INTO game (turn, is_active) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, game.getTurn());
            pstmt.setBoolean(2, game.isActive());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 정보 생성 실패", e);
        }

        throw new IllegalStateException("생성된 게임 ID를 가져올 수 없습니다.");
    }

    public Optional<Game> findById(Long id) {
        Connection conn = transactionManager.getConnection();
        String sql = "SELECT id, turn, is_active FROM game WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Game(
                            rs.getLong("id"),
                            rs.getString("turn"),
                            rs.getBoolean("is_active")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 정보 조회 실패", e);
        }
        return Optional.empty();
    }

    public List<Game> findActiveGames() {
        Connection conn = transactionManager.getConnection();
        String sql = "SELECT id, turn, is_active FROM game WHERE is_active = true";
        List<Game> games = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                games.add(new Game(
                        rs.getLong("id"),
                        rs.getString("turn"),
                        rs.getBoolean("is_active")
                ));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 정보 조회 실패", e);
        }
        return games;
    }

    public void updateIsActive(Long id, boolean isActive) {
        Connection conn = transactionManager.getConnection();
        String sql = "UPDATE game SET is_active = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isActive);
            pstmt.setLong(2, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 변경 실패", e);
        }
    }
}
