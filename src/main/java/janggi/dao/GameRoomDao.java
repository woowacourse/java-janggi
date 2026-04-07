package janggi.dao;

import janggi.dto.GameRoom;
import janggi.dto.NewGameRoom;

import java.sql.Connection;
import java.util.Optional;

public interface GameRoomDao {
    long save(NewGameRoom newGameRoom, Connection connection);

    void update(GameRoom gameRoom, Connection connection);

    Optional<GameRoom> findById(long gameRoomId, Connection connection);
}