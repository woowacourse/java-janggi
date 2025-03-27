package janggi.piece;

import janggi.piece.movement.HorseMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.position.Position;
import java.util.List;

public class Horse extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.HORSE;

    private final MovementRule movementRule;

    public Horse(final Color color) {
        super(color);
        this.movementRule = new HorseMovementRule();
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
