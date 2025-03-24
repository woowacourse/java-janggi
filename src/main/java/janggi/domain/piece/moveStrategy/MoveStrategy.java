package janggi.domain.piece.moveStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.List;
import java.util.Set;

public interface MoveStrategy {

    boolean isMovable(JanggiBoard janggiBoard, Point start, Point end, Set<List<Direction>> directions);
}
