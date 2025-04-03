package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.piece.movement.ElephantMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.position.Position;

public class ElephantMoveable implements Moveable {

    private final Moveable pieceOnPathBlockingRule;
    private final Moveable sameColorBlockingRule;
    private final MovementRule elephantMovementRule;

    public ElephantMoveable() {
        this.pieceOnPathBlockingRule = new PieceOnPathBlockingRule();
        this.sameColorBlockingRule = new SameColorBlockingRule();
        this.elephantMovementRule = new ElephantMovementRule();
    }

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        try {
            elephantMovementRule.validateMovementRule(start, end);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return pieceOnPathBlockingRule.isMoveable(start, end, pieces)
                && sameColorBlockingRule.isMoveable(start, end, pieces);
    }
}
