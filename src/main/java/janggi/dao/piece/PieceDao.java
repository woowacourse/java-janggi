package janggi.dao.piece;

import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PieceDao {

    void saveBoard(Connection connection, Map<Position, Piece> boardInfo, Long gameId);

    List<PieceEntity> findAllPiecesByGameId(Connection connection, long gameId);

    Optional<PieceEntity> findPieceByPosition(Connection connection, Position position);

    void updatePosition(Connection connection, Long pieceId, Position to);

    void deletePieceByPosition(Connection connection, Position position);
}


