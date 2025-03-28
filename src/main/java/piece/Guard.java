package piece;

import board.Board;
import movement.MovePath;
import movement.MovePaths;
import movement.Movement;
import position.Position;
import validator.DistanceCheckable;

import java.util.List;

public class Guard extends Piece implements DistanceCheckable {

    private static final MovePaths moveActions;
    private static final double DISTANCE;

    static {
        moveActions = new MovePaths(List.of(
                new MovePath(Movement.UP),
                new MovePath(Movement.DOWN),
                new MovePath(Movement.LEFT),
                new MovePath(Movement.RIGHT)
        ));

        DISTANCE = moveActions.calculateDistance();
    }

    public Guard(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDistance(src, dest);
    }

    @Override
    public double getExpectedDistance() {
        return DISTANCE;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Guard;
    }
}
