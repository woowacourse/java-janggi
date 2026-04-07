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

public class PieceDao {
    public void savePiecePosition(int gameId, PieceDto pieceDto, PositionDto positionDto) {
        String sql = "INSERT INTO piece_state (game_id, country, piece_name, row_pos, col_pos) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setString(2, pieceDto.countryName());
            pstmt.setString(3, pieceDto.pieceName());
            pstmt.setInt(4, positionDto.row());
            pstmt.setInt(5, positionDto.col());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("보드 상태 저장 중 에러 발생: " + e.getMessage());
        }
    }

    public void movePiece(int gameId, int beforeRow, int beforeCol, int afterRow, int afterCol) {
        String sql = "UPDATE piece_state SET row_pos = ?, col_pos = ? WHERE game_id = ? AND row_pos = ? AND col_pos = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, afterRow);
            pstmt.setInt(2, afterCol);
            pstmt.setInt(3, gameId);
            pstmt.setInt(4, beforeRow);
            pstmt.setInt(5, beforeCol);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("말 이동 업데이트 중 에러 발생: " + e.getMessage());
        }
    }

    public void deletePiece(int gameId, int row, int col) {
        String sql = "DELETE FROM piece_state WHERE game_id = ? AND row_pos = ? AND col_pos = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setInt(2, row);
            pstmt.setInt(3, col);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("말 제거 중 에러 발생: " + e.getMessage());
        }
    }

    public List<SavedPieceDto> getSavedBoard(int gameId) {
        String sql = "SELECT country, piece_name, row_pos, col_pos FROM piece_state WHERE game_id = ?";
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
                                    rs.getInt("row_pos"),
                                    rs.getInt("col_pos")));
                }
            }
        } catch (SQLException e) {
            System.out.println("보드 정보 조회 중 에러 발생: " + e.getMessage());
        }

        return savedPieceDtos;
    }
}
