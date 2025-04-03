package janggi.piece;

import janggi.piece.moveable.CannonMoveable;
import janggi.piece.moveable.Moveable;
import janggi.piece.movement.MovementRule;
import janggi.piece.movement.StraightMovementRule;
import janggi.piece.path.PathCalculator;
import janggi.piece.path.StraightMovementPathCalculator;
import janggi.position.Position;
import java.util.List;

public class Cannon extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.CANNON;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;
    private final Moveable moveable;

    public Cannon(final Color color) {
        super(color);
        this.pathCalculator = new StraightMovementPathCalculator();
        this.movementRule = new StraightMovementRule();
        this.moveable = new CannonMoveable();
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
