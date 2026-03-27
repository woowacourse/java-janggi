package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    List<Position> canMovePositions(Map<Position, Piece> board, Position from, Dynasty dynasty);

    PieceType pieceType();

}
