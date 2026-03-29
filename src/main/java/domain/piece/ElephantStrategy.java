package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Path;
import domain.board.Position;

import java.util.List;

public class ElephantStrategy implements MoveStrategy {
    @Override
    public List<Position> getPathPositions(Position from, Position to) {

        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        if (!((Math.abs(dx) == 3 && Math.abs(dy) == 2) || (Math.abs(dx) == 2 && Math.abs(dy) == 3))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }

        Direction xDirection = decideXDirection(dx);
        Direction yDirection = decideYDirection(dy);

        Direction mainDirection;
        Direction subDirection;
        if (Math.abs(dx) > Math.abs(dy)) {
            mainDirection = xDirection;
            subDirection = yDirection;
        } else {
            mainDirection = yDirection;
            subDirection = xDirection;
        }

        Position step1 = from.next(mainDirection);
        Position step2 = step1.next(mainDirection).next(subDirection);

        return List.of(step1, step2);
    }

    private Direction decideXDirection(int dx) {
        if (dx > 0) {
            return Direction.RIGHT;
        }
        return Direction.LEFT;
    }

    private Direction decideYDirection(int dy) {
        if (dy > 0) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    @Override
    public void canMove(List<Path> paths, Piece to) {
        if (!paths.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.PATH_BLOCKED.getMessage());
        }
    }
}
