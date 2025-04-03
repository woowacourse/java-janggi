package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.piece.movement.KingMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.position.Position;

public class KingMoveable implements Moveable {

    private final Moveable pieceOnPathBlockingRule;
    private final Moveable sameColorBlockingRule;
    private final MovementRule kingMovementRule;

    public KingMoveable() {
        this.pieceOnPathBlockingRule = new PieceOnPathBlockingRule();
        this.sameColorBlockingRule = new SameColorBlockingRule();
        this.kingMovementRule = new KingMovementRule();
    }

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        try {
            kingMovementRule.validateMovementRule(start, end);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return pieceOnPathBlockingRule.isMoveable(start, end, pieces)
                && sameColorBlockingRule.isMoveable(start, end, pieces);
    }
}
