package infra.dao;

import config.JdbcConfig;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import infra.entity.CurrentPiecePositionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrentPiecePositionDao {
    private static final String INSERT_SQL =
            "INSERT INTO current_piece_position (game_id, piece_type, piece_team, board_row, board_column) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_GAME_ID_SQL =
            "SELECT id, game_id, piece_type, piece_team, board_row, board_column FROM current_piece_position WHERE game_id = ?";
    private static final String DELETE_BY_GAME_ID_SQL =
            "DELETE FROM current_piece_position WHERE game_id = ?";

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

    public List<CurrentPiecePositionEntity> findByGameId(Long gameId) {
        List<CurrentPiecePositionEntity> boardEntities = new ArrayList<>();
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_GAME_ID_SQL)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    boardEntities.add(mapToBoardEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.CURRENT_PIECE_POSITION_READ_ERROR.getMessage() + e);
        }
        return boardEntities;
    }

    public void deleteByGameId(Long gameId) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_BY_GAME_ID_SQL)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.CURRENT_PIECE_POSITION_DELETE_ERROR.getMessage() + e);
        }
    }

    private CurrentPiecePositionEntity mapToBoardEntity(ResultSet rs) throws SQLException {
        return new CurrentPiecePositionEntity(
                rs.getLong("id"),
                rs.getLong("game_id"),
                rs.getString("piece_type"),
                rs.getString("piece_team"),
                rs.getInt("board_row"),
                rs.getInt("board_column")
        );
    }
}
