package dao.gameroom;

import dao.converter.GameRoomDto;
import java.sql.Connection;
import java.util.Optional;

public interface GameRoomQueryDao {

    Optional<GameRoomDto> findByName(Connection connection, String gameRoomName);
}
