package janggi.repository;

import janggi.domain.JanggiGame;
import janggi.domain.piece.Team;
import janggi.infrastructure.DBConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    public JanggiGame save(JanggiGame game) {
        String sql = "INSERT INTO game (current_turn, game_status, winner) VALUES (?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, game.findCurrentTeam().name());
            pstmt.setString(2, toGameStatus(game));
            pstmt.setString(3, toWinnerString(game));
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                Long gameId = rs.getLong(1);
                return new JanggiGame(gameId, game.findCurrentTeam(), game.isFinished(), game.findWinner());
            }
            throw new SQLException("게임 ID 생성에 실패했습니다.");

        } catch (SQLException e) {
            throw new RuntimeException("게임 저장에 실패했습니다.", e);
        }
    }

    public List<JanggiGame> findPlayingGames() {
        String sql = "SELECT game_id, current_turn, game_status, winner FROM game WHERE game_status = 'PLAYING'";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            List<JanggiGame> games = new ArrayList<>();

            while (rs.next()) {
                games.add(toJanggiGame(rs));
            }
            return games;

        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임 조회에 실패했습니다.", e);
        }
    }

    public void updateTurn(JanggiGame game) {
        String sql = "UPDATE game SET current_turn = ?, updated_at = datetime('now', 'localtime') WHERE game_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, game.findCurrentTeam().name());
            pstmt.setLong(2, game.findGameId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("턴 갱신에 실패했습니다.", e);
        }
    }

    public void updateFinished(JanggiGame game) {
        String sql = "UPDATE game SET game_status = 'FINISHED', winner = ?, updated_at = datetime('now', 'localtime') WHERE game_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, toWinnerString(game));
            pstmt.setLong(2, game.findGameId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 갱신에 실패했습니다.", e);
        }
    }

    private JanggiGame toJanggiGame(ResultSet rs) throws SQLException {
        Long gameId = rs.getLong("game_id");
        Team currentTurn = Team.valueOf(rs.getString("current_turn"));
        String status = rs.getString("game_status");
        String winnerStr = rs.getString("winner");

        boolean isFinished = "FINISHED".equals(status);
        Team winner = (winnerStr != null) ? Team.valueOf(winnerStr) : null;

        return new JanggiGame(gameId, currentTurn, isFinished, winner);
    }

    private String toGameStatus(JanggiGame game) {
        if (game.isFinished()) {
            return "FINISHED";
        }
        return "PLAYING";
    }

    private String toWinnerString(JanggiGame game) {
        if (game.findWinner() == null) {
            return null;
        }
        return game.findWinner().name();
    }

    public JanggiGame findById(Long gameId) { // PR
        String sql = "SELECT game_id, current_turn, game_status, winner FROM game WHERE game_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new IllegalArgumentException("존재하지 않는 게임입니다. id=" + gameId);
            }

            return toJanggiGame(rs);

        } catch (SQLException e) {
            throw new RuntimeException("게임 조회에 실패했습니다.", e);
        }
    }


}
