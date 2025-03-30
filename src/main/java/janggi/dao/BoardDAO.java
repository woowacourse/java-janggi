package janggi.dao;

import janggi.domain.Board;
import janggi.domain.move.Position;

public interface BoardDAO {

    Board toDomain(String gameRoomName);

    void save(String gameRoomName, Board board);

    void movePiece(String gameRoomName, Position currentPosition, Position targetPosition);
}
