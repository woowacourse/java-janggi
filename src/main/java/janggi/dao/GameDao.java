package janggi.dao;

import janggi.domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class GameDao {

    private final DataSource dataSource;

    public GameDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save(Team turn) {
        String sql = "INSERT INTO game (turn) VALUES (?)";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, turn.name());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new IllegalStateException("[ERROR] 게임 ID 생성에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 저장 중 오류가 발생했습니다.", e);
        }
    }

    public boolean findById(long gameId) {
        String sql = "SELECT 1 FROM game WHERE id = ?";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 조회 중 오류가 발생했습니다.", e);
        }
    }

    public Team findTurnByGameId(long gameId) {
        String sql = "SELECT turn FROM game WHERE id = ?";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Team.valueOf(rs.getString("turn"));
                }
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 게임입니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 조회 중 오류가 발생했습니다.", e);
        }
    }

    public void updateTurn(Connection conn, long gameId, Team nextTurn) {
        String sql = "UPDATE game SET turn = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nextTurn.name());
            pstmt.setLong(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 변경 중 오류가 발생했습니다.", e);
        }
    }
}
