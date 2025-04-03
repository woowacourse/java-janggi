package janggi.piece;

import janggi.piece.moveable.HorseMoveable;
import janggi.piece.moveable.Moveable;
import janggi.piece.movement.HorseMovementRule;
import janggi.piece.movement.MovementRule;
import janggi.piece.path.HorsePathCalculator;
import janggi.piece.path.PathCalculator;
import janggi.position.Position;
import java.util.List;

public class Horse extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.HORSE;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;
    private final Moveable moveable;

    public Horse(final Color color) {
        super(color);
        this.pathCalculator = new HorsePathCalculator();
        this.movementRule = new HorseMovementRule();
        this.moveable = new HorseMoveable();
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
