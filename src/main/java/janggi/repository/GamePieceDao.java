package janggi.repository;

import java.sql.Connection;
import java.util.List;

public interface GamePieceDao {
    List<StoredGamePiece> findByGameId(Connection connection, long gameId);

    void save(Connection connection, long gameId, List<StoredGamePiece> storedGamePieceData);

    void delete(Connection connection, long gameId);
}
