package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.List;

public interface MoveStrategy {

    List<Position> canMovePositions(BoardSnapshot board, Position from, Dynasty dynasty);

}
