package dao.gameroom;

import dao.converter.GameRoomDto;
import domain.piece.character.Team;
import java.util.List;
import queue.DelayedQuery;
import queue.Transaction;

public class GameRoomCommandDaoImpl implements GameRoomCommandDao {

    public void insert(Transaction transaction, GameRoomDto gameRoom) {
        String sql = """
                INSERT INTO game_room (name, turn)
                VALUES(?, ?);
                """;
        transaction.addLast(createDelayedQuery(sql, List.of(gameRoom.name(), gameRoom.turn().name())));
    }

    public void updateTurnByGameRoomName(Transaction transaction, String gameRoomName, Team turn) {
        String sql = """
                UPDATE game_room
                SET turn = ?
                WHERE name = ?
                """;
        transaction.addLast(createDelayedQuery(sql, List.of(turn.name(), gameRoomName)));
    }

    public void deleteByGameRoomName(Transaction transaction, String gameRoomName) {
        String sql = """
                DELETE FROM game_room
                WHERE name = ?
                """;
        transaction.addLast(createDelayedQuery(sql, List.of(gameRoomName)));
    }

    private DelayedQuery createDelayedQuery(String sql, List<Object> params) {
        return new DelayedQuery(sql, params);
    }
}
