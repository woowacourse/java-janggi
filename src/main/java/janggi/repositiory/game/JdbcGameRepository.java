package janggi.repositiory.game;

import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Team;
import janggi.domain.vo.FinishStatus;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final JdbcDataSource dataSource;

    public JdbcGameRepository(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(Connection conn, FinishStatus status, Team turn) throws SQLException {
        String sql = "INSERT INTO game(is_finished, current_turn) VALUES(?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setBoolean(1, status.isFinished());
            pstmt.setString(2, turn.getCode());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("게임 저장 실패");
    }

    @Override
    public Optional<GameData> findLatestGame(Connection conn) {
        String sql = "SELECT id, current_turn, is_finished FROM game ORDER BY id DESC LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return Optional.of(new GameData(
                        rs.getLong("id"),
                        rs.getBoolean("is_finished"),
                        Team.fromCode(rs.getString("current_turn"))
                ));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStatus(Connection conn, Long id, JanggiGame game) {
        String sql = "UPDATE game SET is_finished = ?, current_turn = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, game.getFinishStatus().isFinished());
            pstmt.setString(2, game.getCurrentTeam().getCode());
            pstmt.setLong(3, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("업데이트할 게임을 찾을 수 없습니다. ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("게임 상태 업데이트 중 오류 발생", e);
        }
    }
}
