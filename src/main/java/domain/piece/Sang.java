package domain.piece;

import domain.*;
import domain.piece.move.DistanceMove;

import java.util.function.Predicate;

public class Sang extends Piece implements DistanceMove {

    private static final int SANG_REACHABLE_DISTANCE = 13;
    private static final int SANG_DIRECTION_DISTANCE = 3;
    private static final Predicate<Integer> isReachable = (dist) -> dist == SANG_REACHABLE_DISTANCE;

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public void validatePieceMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableDistanceCoordinate(from, to, isReachable);
        validateDoesNotHasObstacle(board, from, to);
    }

    private void validateDoesNotHasObstacle(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        Direction moveDirection = Direction.getDirection(from, to, SANG_DIRECTION_DISTANCE);
        Direction moveDiagonalDirection = Direction.getDiagonalDirection(from, to);

        JanggiCoordinate firstRoute = from.move(moveDirection);

        if (board.isOccupied(firstRoute) && board.isOccupied(firstRoute.move(moveDiagonalDirection))) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
        }
    }
}
