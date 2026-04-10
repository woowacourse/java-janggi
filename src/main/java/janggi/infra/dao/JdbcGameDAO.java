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

    public JdbcGameDAO(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Long save(GameEntity gameEntity) {
        Connection connection = connectionProvider.getConnection();
        String sql = "INSERT INTO game(room_name, current_turn, last_played_at) VALUES(?, ?, ?)";
        try (
                PreparedStatement pstmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)
        ) {

            pstmt.setString(1, gameEntity.roomName().roomName());
            pstmt.setString(2, gameEntity.currentTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameEntity.lastPlayedAt()));
            pstmt.executeUpdate();
            return getGeneratedKey(pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection);
        }
    }

    @Override
    public Optional<GameEntity> findById(Long id) {
        Connection connection = connectionProvider.getConnection();
        String sql = "SELECT * FROM game WHERE game_id = ?";
        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setLong(1, id);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new GameEntity(
                                resultSet.getLong("game_id"),
                                new RoomName(resultSet.getString("room_name")),
                                Dynasty.valueOf(resultSet.getString("current_turn")),
                                resultSet.getTimestamp("last_played_at").toLocalDateTime()
                        )
                );
            }
            return Optional.empty();
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
    public List<GameEntity> findAllOrderByLastPlayedAtDesc() {
        Connection connection = connectionProvider.getConnection();
        String sql = "SELECT * FROM game ORDER BY last_played_at DESC";
        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {

            List<GameEntity> gameEntities = new ArrayList<>();
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                gameEntities.add(new GameEntity(
                                resultSet.getLong("game_id"),
                                new RoomName(resultSet.getString("room_name")),
                                Dynasty.valueOf(resultSet.getString("current_turn")),
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

    @Override
    public void updateCurrentTurnAndLastPlayedAt(GameEntity gameEntity) {
        Connection connection = connectionProvider.getConnection();
        String sql = "UPDATE game SET current_turn = ?, last_played_at = ? WHERE game_id = ?";

        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setString(1, gameEntity.currentTurn().name());
            pstmt.setTimestamp(2, Timestamp.valueOf(gameEntity.lastPlayedAt()));
            pstmt.setLong(3, gameEntity.id());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection);
        }
    }
}
