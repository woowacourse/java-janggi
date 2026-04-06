package infra.dao;

import config.JdbcConfig;
import controller.dto.CurrentBoardStatus;
import domain.PieceType;
import domain.Position;
import domain.Team;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
    private static final String INSERT_SQL =
            "INSERT INTO board (board_row, board_column, piece_type, team) VALUES (?, ?, ?, ?)";
    private static final String SELECT_SQL =
            "SELECT board_row, board_column, piece_type, team FROM board";
    private static final String DELETE_SQL =
            "DELETE FROM board";

    private final JdbcConfig jdbcConfig;

    public BoardDao(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public void save(List<CurrentBoardStatus> boardStatuses) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {

            for (CurrentBoardStatus status : boardStatuses) {
                pstmt.setInt(1, status.row());
                pstmt.setInt(2, status.column());
                pstmt.setString(3, status.pieceType());
                pstmt.setString(4, status.team());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.BOARD_SAVE_ERROR.getMessage() + e);
        }
    }

    public List<CurrentBoardStatus> findAll() {
        List<CurrentBoardStatus> boardStatuses = new ArrayList<>();

        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                boardStatuses.add(mapToBoardStatus(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.BOARD_READ_ERROR.getMessage() + e);
        }

        return boardStatuses;
    }

    public void deleteAll() {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.BOARD_DELETE_ERROR.getMessage() + e);
        }
    }

    /**
     * 헬퍼 메서드
     */
    private CurrentBoardStatus mapToBoardStatus(ResultSet rs) throws SQLException {
        Position position = Position.from(rs.getInt("board_row"), rs.getInt("board_column"));
        PieceType pieceType = PieceType.getPieceType(rs.getString("piece_type"));
        Team team = Team.getTeam(rs.getString("team"));
        return CurrentBoardStatus.of(position, pieceType, team);
    }
}
