package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.piece.movement.MovementRule;
import janggi.piece.movement.StraightMovementRule;
import janggi.position.Position;

public class CannonMoveable implements Moveable {

    private final Moveable onlyOnePieceOnPathMoveableRule;
    private final Moveable samePieceTypeBlockingRule;
    private final Moveable samePieceTypeOnPathBlockingRule;
    private final Moveable sameColorBlockingRule;
    private final MovementRule straightMovementRule;

    public CannonMoveable() {
        this.onlyOnePieceOnPathMoveableRule = new OnlyOnePieceOnPathMoveableRule();
        this.samePieceTypeBlockingRule = new SamePieceTypeBlockingRule();
        this.samePieceTypeOnPathBlockingRule = new SamePieceTypeOnPathBlockingRule();
        this.sameColorBlockingRule = new SameColorBlockingRule();
        this.straightMovementRule = new StraightMovementRule();
    }

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        try {
            straightMovementRule.validateMovementRule(start, end);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return onlyOnePieceOnPathMoveableRule.isMoveable(start, end, pieces)
                && samePieceTypeBlockingRule.isMoveable(start, end, pieces)
                && samePieceTypeOnPathBlockingRule.isMoveable(start, end, pieces)
                && sameColorBlockingRule.isMoveable(start, end, pieces);
    }
}
