package janggi.repositiory.game;

import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Team;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final JdbcDataSource dataSource;

    public JdbcGameRepository(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(Boolean isFinished, Team currentTurn) {

        String sql = "INSERT INTO game (is_finished, current_turn) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setBoolean(1,isFinished);
            pstmt.setString(2, currentTurn.getCode());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);

                    return generatedId;
                }
                throw new SQLException("게임 저장 후 ID를 가져오지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 저장 중 오류 발생", e);
        }
    }

    @Override
    public Optional<GameData> findLatestGame() {
        String sql = "SELECT id, current_turn, is_finished FROM game ORDER BY id DESC LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
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
    public void update(Long id, JanggiGame game) {
        String sql = "UPDATE game SET is_finished = ?, current_turn = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, game.isFinished());
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

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
