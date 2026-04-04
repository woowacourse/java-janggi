package janggi.infra.dao;

import janggi.domain.position.Position;
import janggi.infra.entity.PiecePositionEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcBoardDAO implements BoardDAO {

    private static final String SAVE_ALL_SQL = "INSERT INTO piece_position(janggi_game_id, piece_row, piece_column, piece_type, dynasty) VALUES";

    private final DataSource dataSource;

    public JdbcBoardDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveAll(List<PiecePositionEntity> piecePositionEntities) {
        if(piecePositionEntities == null || piecePositionEntities.isEmpty()) {
            return;
        }

        try (
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(createSaveAllQuery(piecePositionEntities));
        ) {
            bindParameter(piecePositionEntities, pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createSaveAllQuery(List<PiecePositionEntity> piecePositionEntities) {
        StringBuilder sb = new StringBuilder(SAVE_ALL_SQL);
        for (int i = 0; i < piecePositionEntities.size(); i++) {
            sb.append("(?, ?, ?, ?, ?),");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    private static void bindParameter(List<PiecePositionEntity> piecePositionEntities, PreparedStatement pstmt) throws SQLException {
        int idx = 1;
        for (PiecePositionEntity piecePosition : piecePositionEntities) {
            pstmt.setLong(idx++, piecePosition.gameRoomEntity().id());
            pstmt.setInt(idx++, piecePosition.position().row().row());
            pstmt.setInt(idx++, piecePosition.position().column().column());
            pstmt.setString(idx++, piecePosition.pieceType().name());
            pstmt.setString(idx++, piecePosition.dynasty().name());
        }
    }

    @Override
    public List<PiecePositionEntity> findAllPieceByGameRoomId(Long gameRoomId) {
        return List.of();
    }

    @Override
    public void updatePosition(Long gameRoomId, Position from, Position to) {

    }
}
