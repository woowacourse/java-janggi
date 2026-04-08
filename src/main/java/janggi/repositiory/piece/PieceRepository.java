package janggi.repositiory.piece;

import janggi.domain.piece.Piece;
import janggi.domain.vo.position.Position;

import java.util.List;
import java.util.Map;

public interface PieceRepository {
    void updateALL(Long gameId, Map<Position, Piece> map);
    List<PieceData> findAll(Long gameId);
}
