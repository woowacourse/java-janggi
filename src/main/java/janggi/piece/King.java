package janggi.piece;

import janggi.piece.movement.MovementRule;
import janggi.piece.movement.PalaceMovementRule;
import janggi.position.Position;
import java.util.List;

public class King extends Piece {

    private final MovementRule movementRule;

    public King(final Color color) {
        super(color);
        this.movementRule = new PalaceMovementRule();
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        movementRule.validateMovementRule(start, end);
        return movementRule.calculatePath(start, end);
    }
}
