package dao.gameroom;

import dao.converter.GameRoomDto;
import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GameRoomQueryDaoImpl implements GameRoomQueryDao {

    public Optional<GameRoomDto> findByName(Connection connection, String gameRoomName) {
        String sql = """               
                SELECT name, turn
                FROM game_room gr
                WHERE gr.name = ?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gameRoomName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    GameRoomDto gameRoom = new GameRoomDto(
                            null,
                            resultSet.getString("name"),
                            Team.valueOf(resultSet.getString("turn"))
                    );
                    return Optional.of(gameRoom);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 조회에 실패했습니다 : " + e.getMessage());
        }
        return Optional.empty();
    }
}
