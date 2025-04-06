package dao.gameroom;

import dao.converter.GameRoomDto;
import domain.piece.character.Team;
import java.sql.Connection;
import java.util.Optional;

public interface GameRoomDao {

    boolean insert(Connection connection, GameRoomDto gameRoom);

    boolean updateTurnByGameRoomName(Connection connection, String gameRoomName, Team turn);

    boolean deleteByGameRoomName(Connection connection, String gameRoomName);

    Optional<GameRoomDto> findByName(Connection connection, String gameRoomName);
}
