package infra.dao;

import config.JdbcConfig;
import controller.dto.CurrentBoardStatus;
import domain.Position;
import domain.Team;
import domain.PieceType;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormationPieceLayoutDao {
    private static final String SELECT_BY_TEMPLATE_ID_SQL =
            "SELECT piece_type, board_row, board_column FROM formation_piece_layout WHERE formation_template_id = ?";

    private final JdbcConfig jdbcConfig;

    public FormationPieceLayoutDao(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public List<CurrentBoardStatus> findByTemplateId(Long templateId, Team team) {
        List<CurrentBoardStatus> statuses = new ArrayList<>();
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_TEMPLATE_ID_SQL)) {
            pstmt.setLong(1, templateId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    statuses.add(mapToBoardStatus(rs, team));
                }
                return statuses;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.DEFAULT_INFRA_ERROR.getMessage() + e);
        }
    }

    private CurrentBoardStatus mapToBoardStatus(ResultSet rs, Team team) throws SQLException {
        Position position = Position.from(rs.getInt("board_row"), rs.getInt("board_column"));
        PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
        return CurrentBoardStatus.of(position, pieceType, team);
    }
}
