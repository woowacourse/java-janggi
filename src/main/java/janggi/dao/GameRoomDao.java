package janggi.dao;

import janggi.entity.GameRoomEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRoomDao {
    private final Connection connection;

    public GameRoomDao(Connection connection) {
        this.connection = connection;
    }

    public List<GameRoomEntity> findPlayingGameRooms() {
        final String query = "SELECT id, turn_color, start_time, last_updated FROM GameRoom WHERE is_finished = FALSE ORDER BY last_updated DESC";

        List<GameRoomEntity> gameRoomEntities = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int roomId = resultSet.getInt("id");
                String turnColor = resultSet.getString("turn_color");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp lastUpdated = resultSet.getTimestamp("last_updated");

                gameRoomEntities.add(GameRoomEntity.ofPlaying(roomId, turnColor, startTime.toLocalDateTime(),
                        lastUpdated.toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임방 조회 실패", e);
        }
        return gameRoomEntities;
    }

    public void saveNewRoom(GameRoomEntity gameRoomEntity) {
        String query = "INSERT INTO GameRoom (turn_color, start_time) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameRoomEntity.getTurnColor());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(gameRoomEntity.getStartTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Game State 저장 실패", e);
        }
    }

    public Optional<Integer> findRecentlyRoomId() {
        final String query = "SELECT id FROM GameRoom ORDER BY id DESC LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("가장 최근 Room Id 찾기 실패", e);
        }
    }

    public void updateGameRoom(GameRoomEntity gameRoomEntity) {
        String query = "UPDATE GameRoom SET turn_color = ?, red_score = ?, blue_score = ?, last_updated = CURRENT_TIMESTAMP WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameRoomEntity.getTurnColor());
            preparedStatement.setInt(2, gameRoomEntity.getRedScore());
            preparedStatement.setInt(3, gameRoomEntity.getBlueScore());
            preparedStatement.setInt(4, gameRoomEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Game State update 실패", e);
        }
    }

    public void finishGame(GameRoomEntity gameRoomEntity) {
        String query = "UPDATE GameRoom SET is_finished = ?, winner = ?, end_time = ?, last_updated = CURRENT_TIMESTAMP WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, gameRoomEntity.isFinished());
            preparedStatement.setString(2, gameRoomEntity.getWinner());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(gameRoomEntity.getEndTime()));
            preparedStatement.setInt(4, gameRoomEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Game 종료 update 실패", e);
        }
    }

    public Optional<GameRoomEntity> findById(int roomId) {
        String query = "SELECT id, turn_color, start_time, last_updated, is_finished, winner, end_time, red_score, blue_score FROM GameRoom WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Timestamp endTimeTs = resultSet.getTimestamp("end_time");
                LocalDateTime endTime = null;
                if (endTimeTs != null) {
                    endTime = endTimeTs.toLocalDateTime();
                }

                return Optional.of(new GameRoomEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("turn_color"),
                        resultSet.getTimestamp("start_time").toLocalDateTime(),
                        resultSet.getTimestamp("last_updated").toLocalDateTime(),
                        resultSet.getBoolean("is_finished"),
                        resultSet.getString("winner"),
                        endTime,
                        resultSet.getInt("red_score"),
                        resultSet.getInt("blue_score")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임 상태 찾기 실패", e);
        }
    }
}
