package dao;

import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GameRoomDAO {

    private final Connection connection;

    public GameRoomDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insert(GameRoomEntity gameRoom) {
        final var query = "INSERT INTO game_room VALUES(?, ?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameRoom.name());
            preparedStatement.setString(2, gameRoom.turn().name());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<GameRoomEntity> findByGameRoomName(String gameRoomName) {
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

    public boolean deleteByGameRoomName(String gameRoomName) {
        String sql = """
                DELETE FROM game_room
                WHERE name = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gameRoomName);
            int rowAffected = preparedStatement.executeUpdate();
            System.out.println("rowAffected: " + rowAffected);
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new RuntimeException(sql + ": 실행에 실패했습니다.");
        }
    }
}
