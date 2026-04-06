package janggi.repository;

import janggi.domain.game.Game;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.Map;

public interface PieceRepository {

    void saveAll(Long gameId, Game game);

    void updatePiece(Long gameId, Position from, Position to);

    Map<Position, Piece> findAllByGameId(Long gameId);

}
