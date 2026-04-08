package janggi.infrastructure;

import janggi.domain.position.Position;
import janggi.domain.space.piece.Piece;
import java.util.List;
import java.util.Map;

public interface BoardRepository {
    void saveGame(long gameId, String turn, Map<Position, Piece> arrivePieces);

    List<Long> findAllGameIds();

    Map<Position, Piece> findPiecesByGameId(long gameId);

    String findTurnById(long gameId);

    boolean existsById(long gameId);
}
