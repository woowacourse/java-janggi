package piece;

import board.Board;
import movement.MovePath;
import movement.MovePaths;
import movement.Movement;
import position.Position;
import validator.DirectionCheckable;
import validator.DistanceCheckable;

import java.util.List;
import java.util.function.BiPredicate;

public class Soldier extends Piece implements DirectionCheckable, DistanceCheckable {

    private static final MovePaths basicMoveActions;
    private static final double DISTANCE;

    private final MovePaths moveActions;

    static {
        basicMoveActions = new MovePaths(List.of(
                new MovePath(Movement.RIGHT),
                new MovePath(Movement.LEFT)
        ));

        DISTANCE = basicMoveActions.calculateDistance();
    }

    public Soldier(final Position position, final Country country) {
        super(position, country);
        Movement additionalMovementByCountry = country.getDirection().getForward();
        moveActions = MovePaths.of(basicMoveActions, new MovePath(additionalMovementByCountry));
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDirection(src, dest);
        validateDistance(src, dest);
    }

    @Override
    public BiPredicate<Position, Position> directionRule() {
        return (src, dest) -> canFindCorrectPath(dest);
    }

    private boolean canFindCorrectPath(Position destination) {
        try {
            moveActions.findCorrectMovePath(position, destination);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public double getExpectedDistance() {
        return DISTANCE;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Soldier;
    }
}
