package janggi.piece;

import janggi.piece.movement.GuardMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.piece.path.OneBlockMovementPathCalculator;
import janggi.piece.path.PathCalculator;
import janggi.position.Position;
import java.util.List;

public class Guard extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.GUARD;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;

    public Guard(final Color color) {
        super(color);
        this.pathCalculator = new OneBlockMovementPathCalculator();
        this.movementRule = new GuardMovementRule();
    }

    @Override
    public int getScore() {
        return PIECE_TYPE.getScore();
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        movementRule.validateMovementRule(start, end);
        return pathCalculator.calculatePath(start, end);
    }

    @Override
    public PieceType getPieceType() {
        return PIECE_TYPE;
    }
}
