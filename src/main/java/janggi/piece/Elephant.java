package janggi.piece;

import janggi.piece.movement.ElephantMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.position.Position;
import java.util.List;

public class Elephant extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.ELEPHANT;

    private final MovementRule movementRule;

    public Elephant(final Color color) {
        super(color);
        this.movementRule = new ElephantMovementRule();
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        movementRule.validateMovementRule(start, end);
        return movementRule.calculatePath(start, end);
    }

    @Override
    public PieceType getPieceType() {
        return PIECE_TYPE;
    }
}
