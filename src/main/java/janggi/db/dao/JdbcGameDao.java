package janggi.db.dao;

import janggi.db.DatabaseConnection;
import janggi.db.entity.GameEntity;

import java.sql.*;
import java.util.Optional;

public class JdbcGameDao implements GameDao {

    private final DatabaseConnection connection;

    public JdbcGameDao(final DatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public Long save(final String turn) {
        String sql = "INSERT INTO game (turn) VALUES (?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, turn);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("게임 데이터 저장을 실패했습니다.");
        }
    }

    @Override
    public Optional<GameEntity> findLatest() {
        String sql = "SELECT id, turn " +
                "FROM game " +
                "ORDER BY id DESC LIMIT 1";

        try (Connection conn = connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new GameEntity(
                                rs.getLong("id"),
                                rs.getString("turn")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("최신 게임 데이터 조회를 실패했습니다.");
        }
    }

    @Override
    public void updateTurn(Long gameId, String turn) {
        String sql = "UPDATE game " +
                "SET turn = ? " +
                "WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, turn);
            pstmt.setLong(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 정보 업데이트를 실패했습니다.");
        }
    }

    @Override
    public void deleteById(Long gameId) {
        String sql = "DELETE FROM game " +
                "WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 삭제를 실패했습니다.");
        }
    }
}
