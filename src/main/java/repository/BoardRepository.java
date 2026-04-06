package repository;

import domain.board.Position;
import domain.piece.PieceInfo;
import java.sql.Connection;
import java.util.Map;

public interface BoardRepository {
    void saveAll(Connection connection, Long gameId, Map<Position, PieceInfo> pieceInfos);

    void save(Connection connection, Long gameId, Position to, PieceInfo pieceInfo);

    void delete(Connection connection, Long gameId, Position from);

    Map<Position, PieceInfo> findAllByGameId(Connection connection, Long gameId);
}
