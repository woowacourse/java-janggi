package janggi.repository;

import janggi.dto.GameRoomDto;
import janggi.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public void updateGameMetadata(int gameId, String currentTurn, String status, double choScore, double hanScore) {
        String query = "UPDATE game_room SET current_turn = ?, status = ?, cho_score = ?, han_score = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, currentTurn);
            pstmt.setString(2, status);
            pstmt.setDouble(3, choScore);
            pstmt.setDouble(4, hanScore);
            pstmt.setInt(5, gameId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("게임 메타데이터 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    public List<GameRoomDto> findAll() {
        String query = "SELECT * FROM game_room ORDER BY created_at DESC";
        List<GameRoomDto> games = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                games.add(createGameRoomDto(rs));
            }
            return games;
        } catch (SQLException e) {
            throw new DataAccessException("게임 목록을 조회하는 중 오류가 발생했습니다.", e);
        }
    }

    private GameRoomDto createGameRoomDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String currentTurn = rs.getString("current_turn");
        String status = rs.getString("status");
        double choScore = rs.getDouble("cho_score");
        double hanScore = rs.getDouble("han_score");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return new GameRoomDto(id, currentTurn, status, choScore, hanScore, createdAt);
    }
}
