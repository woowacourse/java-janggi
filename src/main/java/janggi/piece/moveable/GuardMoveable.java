package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.piece.movement.GuardMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.position.Position;

public class GuardMoveable implements Moveable {

    private final Moveable pieceOnPathBlockingRule;
    private final Moveable sameColorBlockingRule;
    private final MovementRule guardMovementRule;

    public GuardMoveable() {
        this.pieceOnPathBlockingRule = new PieceOnPathBlockingRule();
        this.sameColorBlockingRule = new SameColorBlockingRule();
        this.guardMovementRule = new GuardMovementRule();
    }

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        try {
            guardMovementRule.validateMovementRule(start, end);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return pieceOnPathBlockingRule.isMoveable(start, end, pieces)
                && sameColorBlockingRule.isMoveable(start, end, pieces);
    }
}
