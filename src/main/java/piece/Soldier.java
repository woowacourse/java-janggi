package piece;

import board.Board;
import movement.MovePath;
import movement.MovePaths;
import movement.Movement;
import position.Position;
import validator.DirectionCheckable;

import java.util.List;
import java.util.function.BiPredicate;

public class Soldier extends Piece implements DirectionCheckable {

    private static final MovePaths basicMoveActions;

    private final MovePaths moveActions;

    static {
        basicMoveActions = new MovePaths(List.of(
                new MovePath(Movement.RIGHT),
                new MovePath(Movement.LEFT)
        ));
    }

    public Soldier(final Position position, final Country country) {
        super(position, country);
        Movement additionalMovementByCountry = country.getDirection().getForward();
        moveActions = MovePaths.of(basicMoveActions, new MovePath(additionalMovementByCountry));
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateDirection(src, dest);
    }

    @Override
    public BiPredicate<Position, Position> directionRule() {
        return (src, dest) -> canCorrectDiff(dest);
    }

    // TODO 2025. 3. 30. 13:37: 하드코딩
    private boolean canCorrectDiff(Position destination) {
        int diffX = position.x() - destination.x();
        int diffY = position.y() - destination.y();

        int minRangeX = Integer.MAX_VALUE;
        int maxRangeX = Integer.MIN_VALUE;
        int minRangeY = Integer.MAX_VALUE;
        int maxRangeY = Integer.MIN_VALUE;

        for (MovePath movePath : moveActions.getMovePaths()) {
            minRangeX = Math.min(minRangeX, movePath.getMovements().getFirst().x());
            maxRangeX = Math.max(maxRangeX, movePath.getMovements().getFirst().x());
            minRangeY = Math.min(minRangeY, movePath.getMovements().getFirst().y());
            maxRangeY = Math.max(maxRangeY, movePath.getMovements().getFirst().y());
        }

        if (minRangeX <= diffX && diffX <= maxRangeX && minRangeY <= diffY && diffY <= maxRangeY) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Soldier;
    }

    @Override
    public int getScore() {
        return 2;
    }
}
