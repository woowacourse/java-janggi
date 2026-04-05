package janggi.infra.dao;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.RoomName;
import janggi.infra.entity.GameEntity;
import janggi.infra.util.ConnectionProvider;
import janggi.infra.util.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class JdbcGameDAO implements GameDAO {

    private final ConnectionProvider connectionProvider;

    private static final String SAVE_SQL = "INSERT INTO game(room_name, last_turn, last_played_at) VALUES(?, ?, ?)";

    public JdbcGameDAO(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Long save(GameEntity gameEntity) {
        Connection connection = connectionProvider.getConnection();
        try (
                PreparedStatement pstmt = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)
        ) {

            pstmt.setString(1, gameEntity.roomName().roomName());
            pstmt.setString(2, gameEntity.lastTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameEntity.lastPlayedAt()));
            pstmt.executeUpdate();
            return getGeneratedKey(pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection);
        }
    }

    private static long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        ResultSet resultSet = pstmt.getGeneratedKeys();
        if (resultSet.next()) {
            return resultSet.getLong(1);
        }
        throw new SQLException("생성된 GameRoom Id를 가져오지 못했습니다.");
    }

    @Override
    public Optional<GameEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<GameEntity> findAllOrderByLastPlayedAtDESC() {
        Connection connection = connectionProvider.getConnection();
        try (
                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM game ORDER BY last_played_at DESC")
        ) {

            List<GameEntity> gameEntities = new ArrayList<>();
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                gameEntities.add(new GameEntity(
                                resultSet.getLong("game_id"),
                                new RoomName(resultSet.getString("room_name")),
                                Dynasty.valueOf(resultSet.getString("last_turn")),
                                resultSet.getTimestamp("last_played_at").toLocalDateTime()
                        )
                );
            }
            return gameEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection);
        }
    }
}
