package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;
import java.util.List;

public class Cannon extends Piece {

    private static final String CANNOT_JUMP_WITH_CANNON = "포를 넘어갈 수 없습니다.";
    private static final String CANNOT_CAPTURE_CANNON_WITH_CANNON = "포는 포끼리 잡을 수 없습니다.";

    private final List<List<Direction>> paths = List.of(
        List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT));

    public Cannon(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }


    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return movementStrategy.findRoute(paths, sourcePosition, targetPosition);
    }

    @Override
    public void checkRoute(List<Piece> pieces) {
        int count = 0;
        for (Piece piece : pieces) {
            if (piece instanceof Cannon) {
                throw new IllegalArgumentException(CANNOT_JUMP_WITH_CANNON);
            }
            if (!(piece instanceof Empty)) {
                count++;
            }
        }
        if (count != 1) {
            throw new IllegalArgumentException(INVALID_TARGET_POSITION);
        }
    }

    @Override
    public void checkTarget(Piece piece) {
        if (piece.isSameSide(side)) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_OWN_PIECE);
        }

        if (piece instanceof Cannon) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_CANNON_WITH_CANNON);
        }
    }

    @Override
    public String getName() {
        return "포";
    }
}
