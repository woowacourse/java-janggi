package janggi.piece;

import janggi.piece.moveable.ElephantMoveable;
import janggi.piece.moveable.Moveable;
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
    private final Moveable moveable;

    public Elephant(final Color color) {
        super(color);
        this.pathCalculator = new ElephantPathCalculator();
        this.movementRule = new ElephantMovementRule();
        this.moveable = new ElephantMoveable();
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
