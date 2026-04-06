package janggi.repository;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.util.List;
import java.util.Map;

public interface PieceRepository {

    void saveAll(Long gameId, List<PieceEntity> pieces);

    void updatePiece(Long gameId, Position from, Position to);

    Map<Position, Piece> findAllByGameId(Long gameId);

}
