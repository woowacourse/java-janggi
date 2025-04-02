package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import java.util.Set;

public final class Horse extends Piece {

    private static final int LONG_MOVE_DISTANCE = 2;
    private static final int SHORT_MOVE_DISTANCE = 1;
    private static final int STEP = 1;

    public Horse(Camp camp) {
        super(camp, Type.HORSE);
    }

    public static Piece from(Camp camp) {
        return new Horse(camp);
    }

    @Override
    public void validateMove(Movement movement, Board board) {
        validateHorseMove(movement);
        validateObstacleOnRoute(movement, board);
    }

    private void validateHorseMove(Movement movement) {
        if (!isHorseMove(movement.calculateXDistance(), movement.calculateYDistance())) {
            throw new ErrorException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isHorseMove(int xDistance, int yDistance) {
        return (xDistance == LONG_MOVE_DISTANCE && yDistance == SHORT_MOVE_DISTANCE) || (
                xDistance == SHORT_MOVE_DISTANCE && yDistance == LONG_MOVE_DISTANCE);
    }

    private void validateObstacleOnRoute(Movement movement, Board board) {
        Set<Piece> pieces = board.getPiecesByPosition(Set.of(findRoute(movement)));
        if (!pieces.isEmpty()) {
            throw new ErrorException("마는 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Position findRoute(Movement movement) {
        if (isNextPositionOnHorizontal(movement)) {
            return getNextHorizontalPosition(movement.origin(), movement.target());
        }
        return getNextVerticalPosition(movement.origin(), movement.target());
    }

    private boolean isNextPositionOnHorizontal(Movement movement) {
        return movement.calculateXDistance() == LONG_MOVE_DISTANCE;
    }

    private Position getNextHorizontalPosition(Position origin, Position target) {
        if (origin.x() < target.x()) {
            return Position.of(origin.x() + STEP, origin.y());
        }
        return Position.of(origin.x() - STEP, origin.y());
    }

    private Position getNextVerticalPosition(Position origin, Position target) {
        if (origin.y() < target.y()) {
            return Position.of(origin.x(), origin.y() + STEP);
        }
        return Position.of(origin.x(), origin.y() - STEP);
    }
}
