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
    private static final String FIND_ALL_PIECES_BY_GAME_ID_SQL = "SELECT * FROM piece_position WHERE game_id = ?";

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
            pstmt.setLong(idx++, piecePosition.gameId());
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
            while (rs.next()) {
                PiecePositionEntity entity = createPiecePositionEntity(rs);

                result.add(entity);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con);
        }
    }

    @Override
    public void deleteByGameIdAndPosition(Long gameId, Position to) {
        Connection connection = connectionProvider.getConnection();

        String sql = """
                DELETE FROM piece_position
                WHERE game_id = ? AND piece_row = ? AND piece_column = ?
                """;

        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setLong(1, gameId);
            pstmt.setInt(2, to.row().row());
            pstmt.setInt(3, to.column().column());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection);
        }
    }

    private static PiecePositionEntity createPiecePositionEntity(ResultSet rs) throws SQLException {
        return new PiecePositionEntity(
                rs.getLong("piece_position_id"),
                Position.from(
                        rs.getInt("piece_row"),
                        rs.getInt("piece_column")
                ),
                PieceType.valueOf(rs.getString("piece_type")),
                Dynasty.valueOf(rs.getString("dynasty")),
                rs.getLong("game_id")
        );
    }

    @Override
    public void updatePosition(Long gameRoomId, Position from, Position to) {
        Connection connection = connectionProvider.getConnection();

        String sql = """
                UPDATE piece_position
                SET piece_row = ?, piece_column = ?
                WHERE game_id = ? AND piece_row = ? AND piece_column = ?
                """;

        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setInt(1, to.row().row());
            pstmt.setInt(2, to.column().column());

            pstmt.setLong(3, gameRoomId);
            pstmt.setInt(4, from.row().row());
            pstmt.setInt(5, from.column().column());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection);
        }
    }
}
