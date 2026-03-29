package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Path;
import domain.board.Position;

import java.util.List;

public class HorseStrategy implements MoveStrategy {
    @Override
    public List<Position> getPathPositions(Position from, Position to) {

        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        if (!((Math.abs(dx) == 2 && Math.abs(dy) == 1) || (Math.abs(dx) == 1 && Math.abs(dy) == 2))) {
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


        return List.of(step1);
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
