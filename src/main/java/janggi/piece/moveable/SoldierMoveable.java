package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.piece.movement.MovementRule;
import janggi.piece.movement.SoldierMovementRule;
import janggi.position.Position;

public class SoldierMoveable implements Moveable {

    private final Moveable pieceOnPathBlockingRule;
    private final Moveable sameColorBlockingRule;
    private final MovementRule soldierMovementRule;

    public SoldierMoveable() {
        this.pieceOnPathBlockingRule = new PieceOnPathBlockingRule();
        this.sameColorBlockingRule = new SameColorBlockingRule();
        this.soldierMovementRule = new SoldierMovementRule();
    }

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        try {
            soldierMovementRule.validateMovementRule(start, end);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return pieceOnPathBlockingRule.isMoveable(start, end, pieces)
                && sameColorBlockingRule.isMoveable(start, end, pieces);
    }
}
