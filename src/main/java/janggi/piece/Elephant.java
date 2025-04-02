package janggi.piece;

import janggi.piece.movement.ElephantMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.piece.path.ElephantPathCalculator;
import janggi.piece.path.PathCalculator;
import janggi.position.Position;
import java.util.List;

public class Elephant extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.ELEPHANT;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;

    public Elephant(final Color color) {
        super(color);
        this.pathCalculator = new ElephantPathCalculator();
        this.movementRule = new ElephantMovementRule();
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
