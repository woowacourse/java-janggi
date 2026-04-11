package infra.dao;

import config.JdbcConfig;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormationDao {
    private static final String SELECT_ID_BY_NAME_AND_TEAM_SQL =
            "SELECT id FROM formation_template WHERE template_name = ? AND team = ?";
    private static final String SELECT_NAME_BY_ID_SQL =
            "SELECT template_name FROM formation_template WHERE id = ?";

    private final JdbcConfig jdbcConfig;

    public FormationDao(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public Long findIdByNameAndTeam(String pattern, String team) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ID_BY_NAME_AND_TEAM_SQL)) {
            pstmt.setString(1, pattern);
            pstmt.setString(2, team);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
                throw new DatabaseConnectionException(InfraErrorMessage.FORMATION_NOT_FOUND_ERROR.getMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.FORMATION_READ_ERROR.getMessage() + e);
        }
    }

    public String findNameById(Long id) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_NAME_BY_ID_SQL)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("template_name");
                }
                throw new DatabaseConnectionException(InfraErrorMessage.FORMATION_NOT_FOUND_ERROR.getMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.FORMATION_READ_ERROR.getMessage() + e);
        }
    }
}
