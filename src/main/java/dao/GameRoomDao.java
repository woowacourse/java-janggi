package dao;

import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GameRoomDao {

    private boolean executeSql(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
    }

    public boolean insert(Connection connection, GameRoomEntity gameRoom) {
        String sql = "INSERT INTO game_room VALUES(?, ?)";
        return executeSql(connection, sql, List.of(gameRoom.name(), gameRoom.turn().name()));
    }

    public boolean updateTurnByGameRoomName(Connection connection, String gameRoomName, Team turn) {
        String sql = """
                UPDATE game_room
                SET turn = ?
                WHERE name = ?
                """;
        return executeSql(connection, sql, List.of(turn.name(), gameRoomName));
    }

    public boolean deleteByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """
                DELETE FROM game_room
                WHERE name = ?
                """;
        return executeSql(connection, sql, List.of(gameRoomName));
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
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
        return Optional.empty();
    }
}