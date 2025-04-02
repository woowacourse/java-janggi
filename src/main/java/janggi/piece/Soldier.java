package janggi.piece;

import janggi.piece.movement.MovementRule;
import janggi.piece.movement.OneBlockMovementRule;
import janggi.piece.path.OneBlockMovementPathCalculator;
import janggi.piece.path.PathCalculator;
import janggi.position.Direction;
import janggi.position.Position;
import java.util.List;

public class Soldier extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.SOLDIER;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;

    public Soldier(final Color color) {
        super(color);
        this.pathCalculator = new OneBlockMovementPathCalculator();
        this.movementRule = new OneBlockMovementRule();
    }

    @Override
    public int getScore() {
        return PIECE_TYPE.getScore();
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        movementRule.validateMovementRule(start, end);
        validateDirection(start, end);
        return pathCalculator.calculatePath(start, end);
    }

    private void validateDirection(final Position start, final Position end) {
        final Direction direction = start.calculateDirection(end);
        if (color.isReverseFrontVerticalDirection(direction)) {
            throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
        }
    }

    @Override
    public PieceType getPieceType() {
        return PIECE_TYPE;
    }
}
