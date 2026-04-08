package janggi.db.dao;

import janggi.db.entity.GameEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameDao {

    public Long insert(Connection conn, final String turn) {
        String sql = "INSERT INTO game (turn) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, turn);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("게임 데이터 저장을 실패했습니다.");
        }
    }

    public Optional<GameEntity> selectById(Connection conn, Long gameId) {
        String sql = "SELECT id, turn " +
                "FROM game " +
                "WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new GameEntity(
                        rs.getLong("id"),
                        rs.getString("turn")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회를 실패했습니다.");
        }
    }

    public List<GameEntity> selectAll(Connection conn) {
        String sql = "SELECT id, turn " +
                "FROM game " +
                "ORDER BY id";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            List<GameEntity> games = new ArrayList<>();
            while (rs.next()) {
                games.add(new GameEntity(
                        rs.getLong("id"),
                        rs.getString("turn")));
            }
            return games;
        } catch (SQLException e) {
            throw new RuntimeException("게임 목록 조회를 실패했습니다.");
        }
    }

    public void update(Connection conn, Long gameId, String turn) {
        String sql = "UPDATE game " +
                "SET turn = ? " +
                "WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, turn);
            pstmt.setLong(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 정보 업데이트를 실패했습니다.");
        }
    }

    public void deleteById(Connection conn, Long gameId) {
        String sql = "DELETE FROM game " +
                "WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 삭제를 실패했습니다.");
        }
    }
}
