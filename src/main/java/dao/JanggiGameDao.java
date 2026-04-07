package dao;

import config.DBConnection;
import domain.constant.Country;
import dto.GameRecordDto;
import dto.SavedGameDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JanggiGameDao {
    public int createNewGame(String initialTurn) {
        String sql = "INSERT INTO game_state (turn) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, initialTurn);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("새 게임 생성 중 에러 발생: " + e.getMessage());
        }
        throw new RuntimeException("새 게임 생성에 실패했습니다.");
    }

    public void updateTurn(int gameId, String currentTurn) {
        String sql = "UPDATE game_state SET turn = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, currentTurn);
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("턴 정보 업데이트 중 에러 발생: " + e.getMessage());
        }
    }

    public List<SavedGameDto> getSavedGames() {
        String sql = "SELECT id, modified_date FROM game_state WHERE is_finished = FALSE";

        List<SavedGameDto> savedGameDtos = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            LocalDateTime modifiedDate;
            while (rs.next()) {
                modifiedDate = rs.getTimestamp("modified_date").toLocalDateTime();
                savedGameDtos.add(new SavedGameDto(rs.getInt("id"), modifiedDate));
            }
        } catch (SQLException e) {
            System.out.println("게임 목록 조회 중 에러 발생: " + e.getMessage());
        }

        return savedGameDtos;
    }

    public String getSavedTurn(int gameId) {
        String sql = "SELECT turn FROM game_state WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("turn");
                }
            }
        } catch (SQLException e) {
            System.out.println("보드 정보 조회 중 에러 발생: " + e.getMessage());
        }

        return "";
    }

    public void finishGame(int gameId, double choScore, double hanScore) {
        String sql = "UPDATE game_state SET is_finished = TRUE, cho_score = ?, han_score = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setDouble(1, choScore);
            pstmt.setDouble(2, hanScore);
            pstmt.setInt(3, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("게임 종료 중 에러 발생: " + e.getMessage());
        }
    }

    public List<GameRecordDto> getGameRecords() {
        String sql = "SELECT id, turn, cho_score, han_score FROM game_state WHERE is_finished = TRUE";
        List<GameRecordDto> gameRecordDtos = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                gameRecordDtos.add(new GameRecordDto(
                        rs.getInt("id"),
                        Country.valueOf(rs.getString("turn")).getName(),
                        rs.getDouble("cho_score"),
                        rs.getDouble("han_score"))
                );
            }
        } catch (SQLException e) {
            System.out.println("게임 목록 조회 중 에러 발생: " + e.getMessage());
        }

        return gameRecordDtos;
    }
}
