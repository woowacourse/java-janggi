package janggi.infra.dao;

import janggi.domain.position.Position;
import janggi.infra.entity.PiecePositionEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcPiecePositionDAO implements PiecePositionDAO {

    private static final String SAVE_ALL_SQL = "INSERT INTO piece_position(janggi_game_id, piece_row, piece_column, piece_type, dynasty) VALUES";

    private final DataSource dataSource;

    public JdbcPiecePositionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Long> saveAll(List<PiecePositionEntity> piecePositionEntities) {
        if(piecePositionEntities == null || piecePositionEntities.isEmpty()) {
            throw new IllegalArgumentException("저장할 데이터가 존재하지 않습니다.");
        }
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(createSaveAllQuery(piecePositionEntities), RETURN_GENERATED_KEYS);
        ) {
            bindParameter(piecePositionEntities, pstmt);
            pstmt.executeUpdate();

            return getGeneratedKeys(pstmt, piecePositionEntities.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createSaveAllQuery(List<PiecePositionEntity> piecePositionEntities) {
        StringBuilder sb = new StringBuilder(SAVE_ALL_SQL);
        sb.append("(?, ?, ?, ?, ?),".repeat(piecePositionEntities.size()));
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

    private static List<Long> getGeneratedKeys(PreparedStatement pstmt, int size) throws SQLException {
        ResultSet resultSet = pstmt.getGeneratedKeys();
        List<Long> generatedKeys = new ArrayList<>();
        while (resultSet.next()) {
            generatedKeys.add(resultSet.getLong(1));
        }
        if(generatedKeys.size() != size) {
            throw new SQLException("생성된 Piece Position Id를 가져오지 못했습니다.");
        }
        return generatedKeys;
    }

    @Override
    public List<PiecePositionEntity> findAllPieceByGameRoomId(Long gameRoomId) {
        return List.of();
    }

    @Override
    public void updatePosition(Long gameRoomId, Position from, Position to) {

    }
}
