package janggi.piece;

import janggi.piece.moveable.Moveable;
import janggi.piece.moveable.TankMoveable;
import janggi.piece.movement.MovementRule;
import janggi.piece.movement.StraightMovementRule;
import janggi.piece.path.PathCalculator;
import janggi.piece.path.StraightMovementPathCalculator;
import janggi.position.Position;
import java.util.List;

public class Tank extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.TANK;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;
    private final Moveable moveable;

    public Tank(final Color color) {
        super(color);
        this.pathCalculator = new StraightMovementPathCalculator();
        this.movementRule = new StraightMovementRule();
        this.moveable = new TankMoveable();
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
