package janggi.piece;

import janggi.piece.movement.MovementRule;
import janggi.piece.movement.StraightMovementRule;
import janggi.piece.path.OneBlockMovementPathCalculator;
import janggi.piece.path.PathCalculator;
import janggi.piece.path.StraightMovementPathCalculator;
import janggi.position.Position;
import java.util.List;

public class Tank extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.TANK;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;

    public Tank(final Color color) {
        super(color);
        this.pathCalculator = new StraightMovementPathCalculator();
        this.movementRule = new StraightMovementRule();
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
