package domain.piece;

import domain.*;

import java.util.function.Predicate;

public class Sang extends Piece implements DistanceMove {

    private static final int SANG_REACHABLE_DISTANCE = 13;
    private static final Predicate<Integer> isReachAble = (dist) -> dist == SANG_REACHABLE_DISTANCE;

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    private void validateSangMoveStrategy(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableDistanceCoordinate(from, to, isReachAble);
        validateDoesNotHasObstacle(board, from, to);
    }

    private void validateDoesNotHasObstacle(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        Direction moveDirection = getDirection(from, to);
        JanggiCoordinate next = from.move(moveDirection);

        if (board.isOccupied(next)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
        }
    }

    private Direction getDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.moveRightUp().moveRightUp().moveUp().equals(to) || from.moveRightUp().moveRightUp().moveRight().equals(to)) {
            return Direction.RIGHT_UP;
        }
        if (from.moveRightDown().moveRightDown().moveRight().equals(to) || from.moveRightDown().moveRightDown().moveDown().equals(to)) {
            return Direction.RIGHT_DOWN;
        }
        if (from.moveLeftUp().moveLeftUp().moveUp().equals(to) || from.moveLeftUp().moveLeftUp().moveLeft().equals(to)) {
            return Direction.LEFT_UP;
        }
        return Direction.LEFT_DOWN;
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCoordinate(to);
        validateSangMoveStrategy(board, from, to);
        validateTarget(board, from, to);
    }
}
