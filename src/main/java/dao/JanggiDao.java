package dao;

import config.DBConnection;
import dto.PieceDto;
import dto.PositionDto;
import dto.SavedPieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JanggiDao {

    public void createTables() {
        String createGameTable = "CREATE TABLE IF NOT EXISTS game_state (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "turn VARCHAR(10))";

        String createBoardTable = "CREATE TABLE IF NOT EXISTS board_state (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "game_id INT, " +
                "country VARCHAR(10), " +
                "piece_name VARCHAR(20), " +
                "x_pos INT, " +
                "y_pos INT, " +
                "FOREIGN KEY (game_id) REFERENCES game_state(id) ON DELETE CASCADE)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement gameTablePstmt = connection.prepareStatement(createGameTable);
             PreparedStatement boardTablePstmt = connection.prepareStatement(createBoardTable)) {

            gameTablePstmt.execute();
            boardTablePstmt.execute();
        } catch (SQLException e) {
            System.out.println("테이블 생성 중 에러 발생: " + e.getMessage());
        }
    }

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

    public void savePiecePosition(int gameId, PieceDto pieceDto, PositionDto positionDto) {
        String sql = "INSERT INTO board_state (game_id, country, piece_name, x_pos, y_pos) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setString(2, pieceDto.countryName());
            pstmt.setString(3, pieceDto.pieceName());
            pstmt.setInt(4, positionDto.x());
            pstmt.setInt(5, positionDto.y());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("보드 상태 저장 중 에러 발생: " + e.getMessage());
        }
    }

    public void movePiece(int gameId, int beforeX, int beforeY, int afterX, int afterY) {
        String sql = "UPDATE board_state SET x_pos = ?, y_pos = ? WHERE game_id = ? AND x_pos = ? AND y_pos = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, afterX);
            pstmt.setInt(2, afterY);
            pstmt.setInt(3, gameId);
            pstmt.setInt(4, beforeX);
            pstmt.setInt(5, beforeY);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("말 이동 업데이트 중 에러 발생: " + e.getMessage());
        }
    }

    public void deletePiece(int gameId, int x, int y) {
        String sql = "DELETE FROM board_state WHERE game_id = ? AND x_pos = ? AND y_pos = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("말 제거 중 에러 발생: " + e.getMessage());
        }
    }

    public List<Integer> getSaveGames() {
        String sql = "SELECT id FROM game_state";
        List<Integer> gameId = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                gameId.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("게임 목록 조회 중 에러 발생: " + e.getMessage());
        }
        return gameId;
    }

    public List<SavedPieceDto> getSaveBoard(int gameId) {
        String sql = "SELECT country, piece_name, x_pos, y_pos FROM board_state WHERE game_id = ?";
        List<SavedPieceDto> savedPieceDtos = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    savedPieceDtos.add(
                            new SavedPieceDto(
                                    rs.getString("country"),
                                    rs.getString("piece_name"),
                                    rs.getInt("x_pos"),
                                    rs.getInt("y_pos")));
                }
            }
        } catch (SQLException e) {
            System.out.println("보드 정보 조회 중 에러 발생: " + e.getMessage());
        }
        return savedPieceDtos;
    }
}
