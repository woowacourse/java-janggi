package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;

import java.util.ArrayList;
import java.util.List;

public abstract class StraightMovingPiece extends Piece{
    public StraightMovingPiece(PieceType pieceType, Team team) {
        super(pieceType, team);
    }

    @Override
    public List<Offset> getPathPositions(Offset offset) {
        int dx = offset.dx();
        int dy = offset.dy();

        if (!((dx == 0 && dy != 0) || (dx != 0 && dy == 0))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }

        Direction mainDirection;

        int distance;
        if (dx == 0) {
            mainDirection = decideYDirection(dy);
            distance = Math.abs(dy);
        } else {
            mainDirection = decideXDirection(dx);
            distance = Math.abs(dx);
        }

        Offset step = new Offset(0,0);

        List<Offset> route = new ArrayList<>();

        for (int i = 0; i < distance - 1; i++) {
            step = step.move(mainDirection);
            route.add(step);
        }

        return route;
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
}
