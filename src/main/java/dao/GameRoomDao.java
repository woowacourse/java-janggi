package dao;

import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import queue.DelayedQuery;
import queue.MessageQueue;

public class GameRoomDao {

    public void insert(GameRoomEntity gameRoom) {
        String sql = "INSERT INTO game_room VALUES(?, ?)";
        addToMessageQueue(sql, List.of(gameRoom.name(), gameRoom.turn().name()));
    }

    public void updateTurnByGameRoomName(String gameRoomName, Team turn) {
        String sql = """
                UPDATE game_room
                SET turn = ?
                WHERE name = ?
                """;
        addToMessageQueue(sql, List.of(turn.name(), gameRoomName));
    }

    public void deleteByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """
                DELETE FROM game_room
                WHERE name = ?
                """;
        addToMessageQueue(sql, List.of(gameRoomName));
    }

    public Optional<GameRoomEntity> findByName(Connection connection, String gameRoomName) {
        String sql = """               
                SELECT name, turn
                FROM game_room gr
                WHERE gr.name = ?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gameRoomName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    GameRoomEntity gameRoom = new GameRoomEntity(
                            resultSet.getString("name"),
                            Team.valueOf(resultSet.getString("turn"))
                    );
                    return Optional.of(gameRoom);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 조회에 실패했습니다");
        }
        return Optional.empty();
    }

    private void addToMessageQueue(String sql, List<Object> params) {
        MessageQueue.addLast(new DelayedQuery(sql, params));
    }
}