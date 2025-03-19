package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.List;
import java.util.Set;

public class Pawn implements Piece {

    private static final Set<List<Direction>> DIRECTIONS = Set.of(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        for (List<Direction> directions : DIRECTIONS) {
            Point currPoint = start;
            for (Direction direction : directions) {
                currPoint = currPoint.move(direction);
                if (janggiBoard.isExistPiece(currPoint)) {
                    break;
                }
            }
            if (currPoint.isSamePosition(end)) {
                return true;
            }
        }
        return false;
    }
}
