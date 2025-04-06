package dao.gameroom;

import dao.converter.GameRoomDto;
import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Optional;

public class GameRoomDaoImpl implements GameRoomDao {

    public boolean insert(Connection connection, GameRoomDto gameRoom) {
        String sql = """
                INSERT INTO game_room (name, turn)
                VALUES(?, ?);
                """;
        int rowAffected = executeQuery(connection, sql, List.of(gameRoom.name(), gameRoom.turn().name()));
        return rowAffected == 1;
    }

    public boolean updateTurnByGameRoomName(Connection connection, String gameRoomName, Team turn) {
        String sql = """
                UPDATE game_room
                SET turn = ?
                WHERE name = ?;
                """;
        int rowAffected = executeQuery(connection, sql, List.of(turn.name(), gameRoomName));
        return rowAffected == 1;
    }

    public boolean deleteByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """
                DELETE FROM game_room
                WHERE name = ?;
                """;
        int rowAffected = executeQuery(connection, sql, List.of(gameRoomName));
        return rowAffected == 1;
    }

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
            throw new RuntimeException("[ERROR] DB 조회에 실패했습니다. : " + e.getMessage());
        }
        return Optional.empty();
    }

    private int executeQuery(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected;
        } catch (SQLSyntaxErrorException e) {
            throw new IllegalArgumentException("[ERROR]: 잘못된 형식의 쿼리문입니다. " + sql);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] DB 연결이 끊어졌습니다.");
        }
    }
}
