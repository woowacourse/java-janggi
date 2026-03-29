package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;
import domain.Path;

import java.util.List;

public class ElephantStrategy implements MoveStrategy {
    @Override
    public List<Offset> getPathPositions(Offset offset) {

        int dx = offset.dx();
        int dy = offset.dy();

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

        Offset step = new Offset(0, 0);

        Offset step1 = step.move(mainDirection);
        Offset step2 = step1.move(mainDirection).move(subDirection);

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
