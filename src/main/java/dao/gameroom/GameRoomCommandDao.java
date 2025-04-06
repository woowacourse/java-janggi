package dao.gameroom;

import dao.converter.GameRoomDto;
import domain.piece.character.Team;
import java.sql.Connection;

public interface GameRoomCommandDao {

    void insert(Connection connection, GameRoomDto gameRoom);

    void updateTurnByGameRoomName(Connection connection, String gameRoomName, Team turn);

    void deleteByGameRoomName(Connection connection, String gameRoomName);
}
