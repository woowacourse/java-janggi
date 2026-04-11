package janggi.persistence.dao;

import janggi.exception.DuplicateGameException;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static janggi.config.DatabaseConfig.getConnection;

public class JdbcGameDao implements GameDao {
    private static final int SQL_DUPLICATE_CODE = 1062;

    @Override
    public void create(GameEntity gameEntity) {
        String sql = """
                INSERT INTO game (id, name, status, current_turn)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, gameEntity.id());
            pstmt.setString(2, gameEntity.name());
            pstmt.setString(3, gameEntity.status().name());
            pstmt.setString(4, gameEntity.turn().name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            validateDuplicate(e);
            throw new RuntimeException("게임을 생성하는 중 데이터베이스 오류가 발생했습니다.", e);
        }
    }

    private void validateDuplicate(SQLException e) {
        if (e.getErrorCode() == SQL_DUPLICATE_CODE) {
            throw new DuplicateGameException("이미 존재하는 게임입니다.", e);
        }
    }

    @Override
    public List<String> findAllNames() {
        String sql = """
                SELECT name
                FROM game
                """;

        List<String> names = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // 파라미터가 없으므로 바로 실행
            while (rs.next()) {
                names.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("게임 목록을 불러오는 중 오류가 발생했습니다.", e);
        }

        return names;
    }

    @Override
    public Optional<String> findByName(String gameName) {
        String sql = """
                SELECT id FROM game
                WHERE name = ?
                """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, gameName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String gameId = rs.getString("id");
                    return Optional.of(gameId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회 도중 오류가 발생했습니다.", e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(String id) {
        String sql = """
            DELETE FROM game
            WHERE id = ?
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 삭제 도중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<GameEntity> findById(String id) {
        String sql = """
                SELECT * FROM game
                WHERE id = ?
                """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    GameEntity gameEntity = new GameEntity(
                            rs.getString("id"),
                            rs.getString("name"),
                            Status.valueOf(rs.getString("status")),
                            Turn.valueOf(rs.getString("current_turn"))
                    );
                    return Optional.of(gameEntity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회 도중 오류가 발생했습니다.", e);
        }
        return Optional.empty();
    }

    @Override
    public void updateStatus(String gameId, Turn turn, Status status) {
        String sql = """
                UPDATE game
                SET status = ?, current_turn = ?
                WHERE id = ?
                """;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, status.name());
            pstmt.setString(2, turn.name());
            pstmt.setString(3, gameId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("게임 상태를 업데이트하는 중 오류가 발생했습니다.", e);
        }
    }
}
