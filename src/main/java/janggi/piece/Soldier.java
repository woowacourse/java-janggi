package janggi.piece;

import janggi.piece.moveable.Moveable;
import janggi.piece.moveable.SoldierMoveable;
import janggi.piece.movement.MovementRule;
import janggi.piece.movement.SoldierMovementRule;
import janggi.piece.path.OneBlockMovementPathCalculator;
import janggi.piece.path.PathCalculator;
import janggi.position.Direction;
import janggi.position.Position;
import java.util.List;

public class Soldier extends Piece {
    private static final PieceType PIECE_TYPE = PieceType.SOLDIER;

    private final PathCalculator pathCalculator;
    private final MovementRule movementRule;
    private final Moveable moveable;

    public Soldier(final Color color) {
        super(color);
        this.pathCalculator = new OneBlockMovementPathCalculator();
        this.movementRule = new SoldierMovementRule();
        this.moveable = new SoldierMoveable();
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
        validateDirection(start, end);
        return pathCalculator.calculatePath(start, end);
    }

    private void validateDirection(final Position start, final Position end) {
        final Direction direction = Direction.calculateDirection(start, end);
        if (color.isReverseFrontVerticalDirection(direction)) {
            throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
        }
    }

    @Override
    public PieceType getPieceType() {
        return PIECE_TYPE;
    }
}
