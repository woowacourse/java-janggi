package dao.gameroom;

import dao.converter.GameRoomDto;
import domain.piece.character.Team;
import queue.Transaction;

public interface GameRoomCommandDao {

    void insert(Transaction transaction, GameRoomDto gameRoom);

    void updateTurnByGameRoomName(Transaction transaction, String gameRoomName, Team turn);

    void deleteByGameRoomName(Transaction transaction, String gameRoomName);
}
