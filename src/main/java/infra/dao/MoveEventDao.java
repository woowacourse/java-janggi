package infra.dao;

import config.JdbcConfig;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import infra.entity.MoveEventEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveEventDao {
    private static final String INSERT_SQL = 
            "INSERT INTO move_event (game_id, version, from_row, from_column, to_row, to_column) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_GAME_ID_SQL = 
            "SELECT id, game_id, version, from_row, from_column, to_row, to_column " +
            "FROM move_event WHERE game_id = ? ORDER BY version ASC";

    private final JdbcConfig jdbcConfig;

    public MoveEventDao(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public void save(MoveEventEntity entity) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {
            pstmt.setLong(1, entity.getGameId());
            pstmt.setLong(2, entity.getVersion());
            pstmt.setInt(3, entity.getFromRow());
            pstmt.setInt(4, entity.getFromColumn());
            pstmt.setInt(5, entity.getToRow());
            pstmt.setInt(6, entity.getToColumn());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.MOVE_EVENT_SAVE_ERROR.getMessage() + e);
        }
    }

    public List<MoveEventEntity> findByGameId(Long gameId) {
        List<MoveEventEntity> moveEventEntities = new ArrayList<>();
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_GAME_ID_SQL)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    moveEventEntities.add(mapToEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.DEFAULT_INFRA_ERROR.getMessage() + e);
        }
        return moveEventEntities;
    }

    private MoveEventEntity mapToEntity(ResultSet rs) throws SQLException {
        return new MoveEventEntity(
                rs.getLong("id"),
                rs.getLong("game_id"),
                rs.getLong("version"),
                rs.getInt("from_row"),
                rs.getInt("from_column"),
                rs.getInt("to_row"),
                rs.getInt("to_column")
        );
    }
}
