package janggi.repository;

import javax.sql.DataSource;
import janggi.domain.game.GameRoom;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GameRoomRepository {

    private final DataSource dataSource;

    public GameRoomRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long save(GameRoom gameRoom) {
        String sql = "INSERT INTO game_rooms(current_turn, game_status)" +
                " VALUES(?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, gameRoom.getCurrentTurn().name());
            preparedStatement.setString(2, gameRoom.getGameStatus().name());

            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.GAME_ROOM_SAVE_ERROR.getMessage(), e);
        }
    }

    public List<Long> findAllByGameStatus(GameStatus gameStatus) {
        String sql = "SELECT game_room_id " +
                "FROM game_rooms " +
                "WHERE game_status = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, gameStatus.name());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Long> ids = new ArrayList<>();
                while (resultSet.next()) {
                    ids.add(resultSet.getLong("game_room_id"));
                }
                return ids;
            }
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.GAME_ROOM_FIND_ERROR.getMessage(), e);
        }
    }

    public GameRoom findById(long gameRoomId) {
        String sql = "SELECT * FROM game_rooms" +
                " WHERE game_room_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameRoomId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new GameRoom(
                            rs.getLong("game_room_id"),
                            CampType.valueOf(rs.getString("current_turn")),
                            GameStatus.valueOf(rs.getString("game_status")),
                            rs.getTimestamp("start_at").toLocalDateTime(),
                            rs.getTimestamp("end_at") != null
                                    ? rs.getTimestamp("end_at").toLocalDateTime()
                                    : null,
                            rs.getTimestamp("last_updated_at").toLocalDateTime()
                    );
                }
                throw new IllegalArgumentException(ExceptionMessage.GAME_ROOM_NOT_FOUND.getMessage(gameRoomId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.GAME_ROOM_FIND_ERROR.getMessage(gameRoomId), e);
        }
    }

    public void update(GameRoom gameRoom) {
        String sql = "UPDATE game_rooms " +
                "SET current_turn = ?, game_status = ?, start_at = ?,  end_at = ?, last_updated_at = CURRENT_TIMESTAMP" +
                " WHERE game_room_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, gameRoom.getCurrentTurn().name());
            preparedStatement.setString(2, gameRoom.getGameStatus().name());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(gameRoom.getStartAt()));
            preparedStatement.setTimestamp(4, gameRoom.getEndAt() != null
                    ? Timestamp.valueOf(gameRoom.getEndAt())
                    : null);
            preparedStatement.setLong(5, gameRoom.getGameRoomId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ExceptionMessage.GAME_ROOM_UPDATE_ERROR.getMessage(gameRoom.getGameRoomId()), e);
        }
    }
}
