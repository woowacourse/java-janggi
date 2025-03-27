package janggi.piece;

import janggi.piece.movement.MovementRule;
import janggi.piece.movement.StraightMovementRule;
import janggi.position.Position;
import java.util.List;

public class Cannon extends Piece {

    private final MovementRule movementRule;

    public Cannon(final Color color) {
        super(color);
        this.movementRule = new StraightMovementRule();
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        movementRule.validateMovementRule(start, end);
        return movementRule.calculatePath(start, end);
    }
}
