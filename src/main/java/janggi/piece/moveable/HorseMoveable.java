package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.piece.movement.HorseMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.position.Position;

public class HorseMoveable implements Moveable {

    private final Moveable pieceOnPathBlockingRule;
    private final Moveable sameColorBlockingRule;
    private final MovementRule horseMovementRule;

    public HorseMoveable() {
        this.pieceOnPathBlockingRule = new PieceOnPathBlockingRule();
        this.sameColorBlockingRule = new SameColorBlockingRule();
        this.horseMovementRule = new HorseMovementRule();
    }

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        try {
            horseMovementRule.validateMovementRule(start, end);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return pieceOnPathBlockingRule.isMoveable(start, end, pieces)
                && sameColorBlockingRule.isMoveable(start, end, pieces);
    }
}
