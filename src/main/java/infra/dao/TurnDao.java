package infra.dao;

import config.JdbcConfig;
import domain.Team;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {
    private static final String INSERT_SQL =
            "INSERT INTO turn (turn) VALUES (?)";
    private static final String SELECT_SQL =
            "SELECT turn FROM turn";
    private static final String DELETE_SQL =
            "DELETE FROM turn";

    private final JdbcConfig jdbcConfig;

    public TurnDao(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public void save(Team currentTurn) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {
            pstmt.setString(1, currentTurn.name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.TURN_SAVE_ERROR.getMessage() + e);
        }
    }

    public Team findAll() {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return Team.valueOf(rs.getString("turn"));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.TURN_READ_ERROR.getMessage() + e);
        }

        return Team.CHO;
    }

    public void deleteAll() {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.TURN_DELETE_ERROR.getMessage() + e);
        }
    }
}
