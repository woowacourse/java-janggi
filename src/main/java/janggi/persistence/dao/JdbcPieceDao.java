package janggi.persistence.dao;

import janggi.persistence.entity.PieceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static janggi.config.DatabaseConfig.getConnection;

public class JdbcPieceDao implements PieceDao{

    @Override
    public void createAll(List<PieceEntity> entities) {
        String sql = """
            INSERT INTO piece (game_id, piece_name, camp, row_index, column_index)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (PieceEntity piece : entities) {
                pstmt.setString(1, piece.gameId());
                pstmt.setString(2, piece.pieceName());
                pstmt.setString(3, piece.pieceCamp());
                pstmt.setInt(4, piece.row());
                pstmt.setInt(5, piece.column());

                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("게임을 시작하는 중 기물 생성에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<PieceEntity> findByGameId(String gameId) {
        String sql = """
                SELECT * FROM piece
                WHERE game_id = ?
                """;

        List<PieceEntity> pieces = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setString(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()){
                    PieceEntity piece = new PieceEntity(
                            rs.getLong("id"),
                            rs.getString("game_id"),
                            rs.getString("piece_name"),
                            rs.getString("camp"),
                            rs.getInt("row_index"),
                            rs.getInt("column_index"));
                    pieces.add(piece);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pieces;
    }

    @Override
    public void deleteByGameId(String gameId) {
        String sql = "DELETE FROM piece WHERE game_id = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, gameId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("해당 게임의 기물을 삭제하는 데 실패했습니다.", e);
        }
    }

    @Override
    public void updateAll(String gameId, List<PieceEntity> entities) {
        String sql = """
            UPDATE piece
            SET piece_name = ?, camp = ?, row_index = ?, column = ?
            WHERE id = ? AND game_id = ?
            """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (PieceEntity piece : entities) {
                pstmt.setString(1, piece.pieceName());
                pstmt.setString(2, piece.pieceCamp());
                pstmt.setInt(3, piece.row());
                pstmt.setInt(4, piece.column());
                pstmt.setLong(6, piece.id());
                pstmt.setString(7, gameId);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("기물 상태 업데이트 실패", e);
        }
    }
}
