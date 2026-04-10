package janggi.repositiory.piece;

import janggi.domain.piece.Piece;
import janggi.domain.vo.position.Position;

import java.util.Map;

public interface PieceRepository {
    void updateALL(BoardSnapshot boardSnapshot);
    Map<Position, Piece> findAll(Long gameId);
}
