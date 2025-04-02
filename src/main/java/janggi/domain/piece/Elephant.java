package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import java.util.HashSet;
import java.util.Set;

public final class Elephant extends Piece {

    private static final int LONG_MOVE_DISTANCE = 3;
    private static final int SHORT_MOVE_DISTANCE = 2;
    private static final int STEP = 1;
    private static final int DENOMINATOR = 2;

    public Elephant(Camp camp) {
        super(camp, Type.ELEPHANT);
    }

    public static Piece from(Camp camp) {
        return new Elephant(camp);
    }

    @Override
    public void validateMove(Movement movement, Board board) {
        validateElephantMove(movement);
        validateObstacleOnRoute(movement, board);
    }

    private void validateElephantMove(Movement movement) {
        if (!isElephantMove(movement.calculateXDistance(), movement.calculateYDistance())) {
            throw new ErrorException("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
        }
    }

    private boolean isElephantMove(int xDistance, int yDistance) {
        return (xDistance == LONG_MOVE_DISTANCE && yDistance == SHORT_MOVE_DISTANCE) || (
                xDistance == SHORT_MOVE_DISTANCE && yDistance == LONG_MOVE_DISTANCE);
    }

    private void validateObstacleOnRoute(Movement movement, Board board) {
        Set<Piece> pieces = board.getPiecesByPosition(findRoute(movement));
        if (!pieces.isEmpty()) {
            throw new ErrorException("상은 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Set<Position> findRoute(Movement movement) {
        Set<Position> route = new HashSet<>();
        if (isNextPositionOnHorizontal(movement)) {
            return findHorizontalRoute(movement, route);
        }
        return findVerticalRoute(movement, route);
    }

    private boolean isNextPositionOnHorizontal(Movement movement) {
        return movement.calculateXDistance() == LONG_MOVE_DISTANCE;
    }

    private Set<Position> findHorizontalRoute(Movement movement, Set<Position> route) {
        Position firstPosition = getNextHorizontalPosition(movement.origin(), movement.target());
        route.add(firstPosition);
        route.add(findSecondPosition(movement.target(), firstPosition));
        return route;
    }

    private Set<Position> findVerticalRoute(Movement movement, Set<Position> route) {
        Position firstPosition = getNextVerticalPosition(movement.origin(), movement.target());
        route.add(firstPosition);
        route.add(findSecondPosition(movement.target(), firstPosition));
        return route;
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

    private Position findSecondPosition(Position target, Position firstPosition) {
        return Position.of((firstPosition.x() + target.x()) / DENOMINATOR,
                (firstPosition.y() + target.y()) / DENOMINATOR);
    }
}
