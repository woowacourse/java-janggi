package janggi.repository;

import janggi.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDao {
    public int createGame(String currentTurn, String status, double choScore, double hanScore) {
        String query = "INSERT INTO game_room (current_turn, status, cho_score, han_score) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, currentTurn);
            pstmt.setString(2, status);
            pstmt.setDouble(3, choScore);
            pstmt.setDouble(4, hanScore);
            pstmt.executeUpdate();

            return getGeneratedId(pstmt);
        } catch (SQLException e) {
            throw new DataAccessException("새 게임 생성 중 오류가 발생했습니다.", e);
        }
    }

    private int getGeneratedId(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DataAccessException("ID를 생성할 수 없습니다.");
        }
    }

    public void updateGameStatus(int gameId, String currentTurn, double choScore, double hanScore) {
        String query = "UPDATE game_room SET current_turn = ?, cho_score = ?, han_score = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, currentTurn);
            pstmt.setDouble(2, choScore);
            pstmt.setDouble(3, hanScore);
            pstmt.setInt(4, gameId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("게임 상태 업데이트 중 오류가 발생했습니다.", e);
        }
    }
}
