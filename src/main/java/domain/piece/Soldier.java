package domain.piece;

import domain.board.Board;
import domain.movement.MovePath;
import domain.movement.MovePaths;
import domain.movement.Movement;
import domain.position.Position;
import validator.DirectionCheckable;

import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.function.BiPredicate;

public class Soldier extends Piece implements DirectionCheckable {

    private static final MovePaths basicMoveActions;
    private static final int SOLDIER_SCORE = 2;

    private final MovePaths moveActions;

    static {
        basicMoveActions = new MovePaths(Set.of(
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

    private boolean canCorrectDiff(Position destination) {
        int diffX = position.x() - destination.x();
        int diffY = position.y() - destination.y();

        IntSummaryStatistics xStats = moveActions.xRange();
        IntSummaryStatistics yStats = moveActions.yRange();

        return xStats.getMin() <= diffX && diffX <= xStats.getMax()
                && yStats.getMin() <= diffY && diffY <= yStats.getMax();
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Soldier;
    }

    @Override
    public int getScore() {
        return SOLDIER_SCORE;
    }
}
