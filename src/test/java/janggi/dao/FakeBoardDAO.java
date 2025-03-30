package janggi.dao;

import janggi.domain.Board;
import janggi.domain.move.Position;
import java.util.HashMap;
import java.util.Map;

public class FakeBoardDAO implements BoardDAO {

    private final Map<String, Board> database = new HashMap<>();

    @Override
    public Board toDomain(String gameRoomName) {
        if (!database.containsKey(gameRoomName)){
            throw new IllegalArgumentException();
        }

        return database.get(gameRoomName);
    }

    @Override
    public void save(String gameRoomName, Board board) {
        validGameRoomName(gameRoomName);

        database.put(gameRoomName, board);
    }

    @Override
    public void movePiece(String gameRoomName, Position currentPosition, Position targetPosition) {
        if (!database.containsKey(gameRoomName)){
            throw new IllegalArgumentException();
        }


    }

    private void validGameRoomName(String gameRoomName) {
        if (database.containsKey(gameRoomName)) {
            throw new IllegalArgumentException();
        }
    }
}
