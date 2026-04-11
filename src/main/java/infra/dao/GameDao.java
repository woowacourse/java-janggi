package infra.dao;

import config.JdbcConfig;
import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import infra.entity.GameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameDao {
    private static final String INSERT_SQL =
            "INSERT INTO game (name, current_turn, cho_formation_id, han_formation_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_NAME_SQL =
            "SELECT id, name, current_turn, cho_formation_id, han_formation_id FROM game WHERE name = ?";

    private final JdbcConfig jdbcConfig;

    public GameDao(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public Long save(GameEntity entity) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getCurrentTurn());
            pstmt.setLong(3, entity.getChoFormationId());
            pstmt.setLong(4, entity.getHanFormationId());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new DatabaseConnectionException(InfraErrorMessage.PK_GENERATION_ERROR.getMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.GAME_SAVE_ERROR.getMessage() + e);
        }
    }

    public Optional<GameEntity> findByName(String name) {
        try (Connection conn = jdbcConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_NAME_SQL)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToGameEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.GAME_READ_ERROR.getMessage() + e);
        }
        return Optional.empty();
    }

    private GameEntity mapToGameEntity(ResultSet rs) throws SQLException {
        return new GameEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("current_turn"),
                rs.getLong("cho_formation_id"),
                rs.getLong("han_formation_id")
        );
    }
}
