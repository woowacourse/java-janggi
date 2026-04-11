package infra.dao;

import config.JdbcConfig;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import infra.entity.CurrentPiecePositionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CurrentPiecePositionDao {
    private static final String INSERT_SQL =
            "INSERT INTO current_piece_position (game_id, piece_type, piece_team, board_row, board_column) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_GAME_ID_SQL =
            "SELECT id, game_id, piece_type, piece_team, board_row, board_column FROM current_piece_position WHERE game_id = ?";

    private final JdbcConfig jdbcConfig;

    public CurrentPiecePositionDao(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public void save(Long gameId, List<CurrentPiecePositionEntity> pieceEntities) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {

            for (CurrentPiecePositionEntity entity : pieceEntities) {
                pstmt.setLong(1, gameId);
                pstmt.setString(2, entity.getPieceType());
                pstmt.setString(3, entity.getPieceTeam());
                pstmt.setInt(4, entity.getBoardRow());
                pstmt.setInt(5, entity.getBoardColumn());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.CURRENT_PIECE_POSITION_SAVE_ERROR.getMessage() + e);
        }
    }
}
