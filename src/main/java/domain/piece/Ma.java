package domain.piece;

import domain.*;

public class Ma extends Piece implements DistanceMove {
    private static final int MA_REACHABLE_DISTANCE = 5;

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    private void validateMaMoveStrategy(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to, MA_REACHABLE_DISTANCE);
        validateDoesNotHasObstacle(board, from, to);
    }

    private void validateDoesNotHasObstacle(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        Direction moveDirection = getDirection(from, to);
        if (moveDirection == Direction.UP && !board.isOccupied(from.moveUp())) {
            return;
        }
        if (moveDirection == Direction.RIGHT && !board.isOccupied(from.moveRight())) {
            return;
        }
        if (moveDirection == Direction.DOWN && !board.isOccupied(from.moveDown())) {
            return;
        }
        if (moveDirection == Direction.LEFT && !board.isOccupied(from.moveLeft())) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateMaMoveStrategy(board, from, to);
        validateTarget(board, to);
    }

    private Direction getDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.moveUp().moveRightUp().equals(to) || from.moveUp().moveLeftUp().equals(to)) {
            return Direction.UP;
        }
        if (from.moveRight().moveRightUp().equals(to) || from.moveRight().moveRightDown().equals(to)) {
            return Direction.RIGHT;
        }
        if (from.moveDown().moveRightDown().equals(to) || from.moveDown().moveLeftDown().equals(to)) {
            return Direction.DOWN;
        }
        return Direction.LEFT;
    }
}
