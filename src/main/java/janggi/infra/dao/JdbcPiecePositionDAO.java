package janggi.infra.dao;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.RoomName;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.infra.entity.GameEntity;
import janggi.infra.entity.PiecePositionEntity;
import janggi.infra.util.ConnectionProvider;
import janggi.infra.util.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcPiecePositionDAO implements PiecePositionDAO {

    private static final String SAVE_ALL_SQL = "INSERT INTO piece_position(game_id, piece_row, piece_column, piece_type, dynasty) VALUES";
    private static final String FIND_ALL_PIECES_BY_GAME_ID_SQL = """
                SELECT 
                    pp.piece_position_id,
                    pp.game_id,
                    pp.piece_row,
                    pp.piece_column,
                    pp.piece_type,
                    pp.dynasty,
                    g.room_name,
                    g.current_turn,
                    g.last_played_at
                FROM piece_position pp
                JOIN game g ON pp.game_id = g.game_id
                WHERE pp.game_id = ?
            """;

    private final ConnectionProvider connectionProvider;

    public JdbcPiecePositionDAO(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public List<Long> saveAll(List<PiecePositionEntity> piecePositionEntities) {
        if (piecePositionEntities == null || piecePositionEntities.isEmpty()) {
            throw new IllegalArgumentException("저장할 데이터가 존재하지 않습니다.");
        }

        Connection con = connectionProvider.getConnection();
        try (
                PreparedStatement pstmt = con.prepareStatement(createSaveAllQuery(piecePositionEntities), RETURN_GENERATED_KEYS);
        ) {
            bindParameter(piecePositionEntities, pstmt);
            pstmt.executeUpdate();

            return getGeneratedKeys(pstmt, piecePositionEntities.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con);
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
        if (generatedKeys.size() != size) {
            throw new SQLException("생성된 Piece Position Id를 가져오지 못했습니다.");
        }
        return generatedKeys;
    }

    @Override
    public List<PiecePositionEntity> findAllPiecesByGameId(Long gameId) {

        Connection con = connectionProvider.getConnection();

        try (
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL_PIECES_BY_GAME_ID_SQL)
        ) {
            pstmt.setLong(1, gameId);

            ResultSet rs = pstmt.executeQuery();
            List<PiecePositionEntity> result = new ArrayList<>();
            GameEntity gameEntity = null;
            while (rs.next()) {
                if(gameEntity == null) {
                    gameEntity = createGameEntity(rs);
                }
                PiecePositionEntity entity = createPiecePositionEntity(rs, gameEntity);

                result.add(entity);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con);
        }
    }

    private static PiecePositionEntity createPiecePositionEntity(ResultSet rs, GameEntity gameEntity) throws SQLException {
        return new PiecePositionEntity(
                rs.getLong("piece_position_id"),
                Position.from(
                        rs.getInt("piece_row"),
                        rs.getInt("piece_column")
                ),
                PieceType.valueOf(rs.getString("piece_type")),
                Dynasty.valueOf(rs.getString("dynasty")),
                gameEntity
        );
    }

    private static GameEntity createGameEntity(ResultSet rs) throws SQLException {
        return new GameEntity(
                rs.getLong("game_id"),
                new RoomName(rs.getString("room_name")),
                Dynasty.valueOf(rs.getString("current_turn")),
                rs.getTimestamp("last_played_at").toLocalDateTime()
        );
    }

    @Override
    public void updatePosition(Long gameRoomId, Position from, Position to) {

    }
}
