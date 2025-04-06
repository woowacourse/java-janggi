package dao.gameroom;

import dao.converter.GameRoomDto;
import domain.piece.character.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class GameRoomCommandDaoImpl implements GameRoomCommandDao {

    public void insert(Connection connection, GameRoomDto gameRoom) {
        String sql = """
                INSERT INTO game_room (name, turn)
                VALUES(?, ?);
                """;
        executeQuery(connection, sql, List.of(gameRoom.name(), gameRoom.turn().name()));
    }

    public void updateTurnByGameRoomName(Connection connection, String gameRoomName, Team turn) {
        String sql = """
                UPDATE game_room
                SET turn = ?
                WHERE name = ?
                """;
        executeQuery(connection, sql, List.of(turn.name(), gameRoomName));
    }

    public void deleteByGameRoomName(Connection connection, String gameRoomName) {
        String sql = """
                DELETE FROM game_room
                WHERE name = ?
                """;
        executeQuery(connection, sql, List.of(gameRoomName));
    }

    private void executeQuery(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            throw new IllegalArgumentException("[ERROR]: 잘못된 형식의 쿼리문입니다. " + sql);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] DB 연결이 끊어졌습니다.");
        }
    }
}
