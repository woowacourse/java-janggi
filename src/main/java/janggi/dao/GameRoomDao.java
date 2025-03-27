package janggi.dao;

import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRoomDao {
    private final Connection connection;

    public GameRoomDao(Connection connection) {
        this.connection = connection;
    }

    public List<GameRoomDto> findPlayingGameRooms() {
        final String query = "SELECT id, board_id, turn_color, start_time, last_updated FROM GameRoom WHERE is_finished = FALSE ORDER BY last_updated DESC";

        List<GameRoomDto> gameRooms = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int roomId = resultSet.getInt("id");
                int boardId = resultSet.getInt("board_id");
                String turnColor = resultSet.getString("turn_color");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp lastUpdated = resultSet.getTimestamp("last_updated");

                gameRooms.add(GameRoomDto.createForShowRooms(roomId, boardId, turnColor, startTime, lastUpdated));
            }
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임방 조회 실패", e);
        }
        return gameRooms;
    }

    public Optional<GameRoomDto> findGameRoomById(int roomId) {
        final String query = "SELECT id, board_id, turn_color, start_time, last_updated FROM GameRoom WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int boardId = resultSet.getInt("board_id");
                String turnColor = resultSet.getString("turn_color");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp lastUpdated = resultSet.getTimestamp("last_updated");

                return Optional.of(GameRoomDto.createForShowRooms(roomId, boardId, turnColor, startTime, lastUpdated));
            }
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임방 조회 실패", e);
        }
        return Optional.empty();
    }

    public void saveNewRoom(int boardId, TeamColor turnColor) {
        String query = "INSERT INTO GameRoom (board_id, turn_color, start_time) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);
            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            preparedStatement.setString(2, turnColor.name());
            preparedStatement.setTimestamp(3, startTime);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Game State 저장 실패", e);
        }
    }

    public Optional<Integer> findBoardIdByRoomId(int roomId) {
        final String query = "SELECT board_id FROM GameRoom WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("board_id"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임방 조회 실패", e);
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

    public void updateGameRoom(int rooId, TeamColor turnColor, int redScore, int blueScore) {
        String query = "UPDATE GameRoom SET turn_color = ?, red_score = ?, blue_score = ?, last_updated = CURRENT_TIMESTAMP WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnColor.name());
            preparedStatement.setInt(2, redScore);
            preparedStatement.setInt(3, blueScore);
            preparedStatement.setInt(4, rooId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Game State update 실패", e);
        }
    }

    public void finishGame(int roomId, TeamColor winner) {
        String query = "UPDATE GameRoom SET is_finished = TRUE, winner = ?, end_time = ?, last_updated = CURRENT_TIMESTAMP WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Timestamp endTime = new Timestamp(System.currentTimeMillis());

            preparedStatement.setString(1, winner.name());
            preparedStatement.setTimestamp(2, endTime);
            preparedStatement.setInt(3, roomId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Game 종료 update 실패", e);
        }
    }

    public Optional<GameRoomDto> findRoomFromId(int roomId) {
        String query = "SELECT turn_color, red_score, blue_score FROM GameRoom WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String turnColor = resultSet.getString("turn_color");
                int redScore = resultSet.getInt("red_score");
                int blueScore = resultSet.getInt("blue_score");

                return Optional.of(GameRoomDto.createForState(roomId, turnColor, redScore, blueScore));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임 상태 찾기 실패", e);
        }
    }
}
