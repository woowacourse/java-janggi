package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

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
            Path path = from.generatePath(directions);

            if (path.isArrived(to) && isNotBlocked(path, board)) {
                return true;
            }
        }

        return false;
    }

    private boolean isNotBlocked(Path path, BoardView board) {
        for (int step = 0; step < path.destinationStep(); step++) {
            if (!board.isEmptyPosition(path.positionAt(step))) {
                return false;
            }
        }

        return true;
    }
}
