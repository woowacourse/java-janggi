package janggi.persistence.dao;

import janggi.config.ConnectionPool;
import janggi.config.PooledConnection;
import janggi.domain.Camp;
import janggi.exception.DuplicateGameException;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameDao implements GameDao {
    private final ConnectionPool pool;

    public JdbcGameDao(ConnectionPool pool) {
        this.pool = pool;
    }

    private static final String MYSQL_DUPLICATE_STATE = "23000";
    private static final String H2_DUPLICATE_STATE = "23505";

    @Override
    public void create(Connection conn, GameEntity gameEntity) {
        String sql = """
                INSERT INTO game (id, name, status, current_turn)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gameEntity.id());
            pstmt.setString(2, gameEntity.name());
            pstmt.setString(3, gameEntity.status().name());
            pstmt.setString(4, gameEntity.camp().name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Message: " + e.getMessage());
            validateDuplicate(e);
            throw new RuntimeException("게임을 생성하는 중 데이터베이스 오류가 발생했습니다.", e);
        }
    }

    private void validateDuplicate(SQLException e) {
        String state = e.getSQLState();
        if (MYSQL_DUPLICATE_STATE.equals(state) || H2_DUPLICATE_STATE.equals(state)) {
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

        try (PooledConnection pooled = pool.getPooledConnection();
             PreparedStatement pstmt = pooled.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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

        try (PooledConnection pooled = pool.getPooledConnection();
             PreparedStatement pstmt =pooled.getConnection().prepareStatement(sql)) {
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
    public void deleteById(Connection conn, String id) {
        String sql = """
            DELETE FROM game
            WHERE id = ?
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

        try (PooledConnection pooled = pool.getPooledConnection();
             PreparedStatement pstmt = pool.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    GameEntity gameEntity = new GameEntity(
                            rs.getString("id"),
                            rs.getString("name"),
                            Status.valueOf(rs.getString("status")),
                            Camp.valueOf(rs.getString("current_turn"))
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
    public void updateStatus(Connection conn, String gameId, Camp camp, Status status) {
        String sql = """
                UPDATE game
                SET status = ?, current_turn = ?
                WHERE id = ?
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.name());
            pstmt.setString(2, camp.name());
            pstmt.setString(3, gameId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("게임 상태를 업데이트하는 중 오류가 발생했습니다.", e);
        }
    }
}
