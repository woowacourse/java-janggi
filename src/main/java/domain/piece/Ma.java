package domain.piece;

import domain.*;
import domain.piece.move.DistanceMove;

import java.util.function.Predicate;

public class Ma extends Piece implements DistanceMove {
    private static final int MA_REACHABLE_DISTANCE = 5;
    private static final int MA_DIRECTION_DISTANCE = 2;
    private static final Predicate<Integer> isReachable = (dist) -> dist == MA_REACHABLE_DISTANCE;

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public void validatePieceMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableDistanceCoordinate(from, to, isReachable);
        validateDoesNotHasObstacle(board, from, to);
    }

    private void validateDoesNotHasObstacle(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        Direction moveDirection = Direction.getDirection(from, to, MA_DIRECTION_DISTANCE);
        JanggiCoordinate directionCoordinate = from.move(moveDirection);

        if (!board.isOccupied(directionCoordinate)) {
            return;
        }

        throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
    }
}
