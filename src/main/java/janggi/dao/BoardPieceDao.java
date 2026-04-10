package janggi.dao;

import janggi.dto.BoardPiece;

import java.sql.Connection;
import java.util.List;

public interface BoardPieceDao {
    void insertAll(List<BoardPiece> boardPiece, Connection connection);

    List<BoardPiece> findAllByGameRoomId(long gameRoomId, Connection connection);

    void updatePosition(long gameRoomId, int sourceRow, int sourceCol, int destinationRow, int destinationCol, Connection connection);

    void deleteByPosition(long gameRoomId, int rowPos, int colPos, Connection connection);
}