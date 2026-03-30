package janggi.domain.mouveRule;

import janggi.domain.board.BoardView;
import janggi.domain.Direction;
import janggi.domain.vo.Position;
import java.util.List;

public class ElephantMoveRule implements MoveRule {
    private static final List<List<Direction>> ELEPHANT_PATHS = List.of(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST),
            List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST),
            List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST),
            List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST),
            List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST),
            List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST),
            List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST),
            List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST));


    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        for (List<Direction> directions : ELEPHANT_PATHS) {
            List<Position> path = from.generatePath(directions);

            if (isArrived(path, to) && isNotBlocked(path, board)){
                return true;
            }
        }

        return false;
    }

    private boolean isArrived(List<Position> path, Position to) {
        return !path.isEmpty() && path.get(lastStep(path)).equals(to);
    }

    private boolean isNotBlocked(List<Position> path, BoardView board) {
        for (int step = 0; step < lastStep(path); step++) {
            if (!board.isEmptyPosition(path.get(step))) {
                return false;
            }
        }

        return true;
    }
    private int lastStep(List<Position> path) {
        return path.size()-1;
    }
}
