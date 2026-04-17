package janggi.repository;

import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.PieceFactory;
import janggi.domain.Position;
import janggi.domain.Row;
import janggi.domain.Team;
import janggi.exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceDao {
    public void savePiece(int gameId, int rowIndex, int colIndex, String pieceType, String team) {
        String query = "INSERT INTO piece_position (game_id, row_index, col_index, piece_type, team) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, gameId);
            pstmt.setInt(2, rowIndex);
            pstmt.setInt(3, colIndex);
            pstmt.setString(4, pieceType);
            pstmt.setString(5, team);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("기물 정보 저장 중 오류가 발생했습니다.", e);
        }
    }

    public void updatePiecePosition(int gameId, int fromRow, int fromCol, int toRow, int toCol) {
        String query = "UPDATE piece_position SET row_index = ?, col_index = ? " +
                "WHERE game_id = ? AND row_index = ? AND col_index = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, toRow);
            pstmt.setInt(2, toCol);
            pstmt.setInt(3, gameId);
            pstmt.setInt(4, fromRow);
            pstmt.setInt(5, fromCol);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("기물 이동 정보 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    public void deleteCapturedPiece(int gameId, int row, int col) {
        String query = "DELETE FROM piece_position WHERE game_id = ? AND row_index = ? AND col_index = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, gameId);
            pstmt.setInt(2, row);
            pstmt.setInt(3, col);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("포획된 기물 삭제 중 오류가 발생했습니다.", e);
        }
    }

    public Map<Position, Piece> findAllByGameId(int gameId) {
        String query = "SELECT * FROM piece_position WHERE game_id = ?";
        Map<Position, Piece> pieces = new HashMap<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    addPieceFromResultSet(pieces, rs);
                }
            }
            return pieces;
        } catch (SQLException e) {
            throw new DataAccessException("기물 목록을 불러오는 중 오류가 발생했습니다.", e);
        }
    }

    private void addPieceFromResultSet(Map<Position, Piece> pieces, ResultSet rs) throws SQLException {
        int row = rs.getInt("row_index");
        int col = rs.getInt("col_index");
        String type = rs.getString("piece_type");
        String team = rs.getString("team");

        Position position = Position.of(Row.of(row), Column.of(col));
        Piece piece = PieceFactory.create(type, Team.valueOf(team));
        pieces.put(position, piece);
    }
}
