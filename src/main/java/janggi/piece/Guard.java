package janggi.piece;

import janggi.piece.moveable.GuardMoveable;
import janggi.piece.moveable.Moveable;
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
    private final Moveable moveable;

    public Guard(final Color color) {
        super(color);
        this.pathCalculator = new OneBlockMovementPathCalculator();
        this.movementRule = new GuardMovementRule();
        this.moveable = new GuardMoveable();
    }

    @Override
    public int getScore() {
        return PIECE_TYPE.getScore();
    }

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        return moveable.isMoveable(start, end, pieces);
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
