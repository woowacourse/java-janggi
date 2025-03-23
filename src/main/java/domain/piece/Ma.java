package domain.piece;

import domain.*;

public class Ma extends Piece {
    private static final int MA_REACHABLE_RADIUS = 5;

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateMoveCoordinate(board, from, to);
        validateMaMoveStrategy(board, from, to);
        validateTarget(board, to);
    }

    private void validateMaMoveStrategy(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to);
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

    private void validateMoveCoordinate(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (board.isOutOfBoundary(from) || board.isOutOfBoundary(to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 좌표위치 입니다.");
        }
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    private void validateReachableCoordinate(JanggiCoordinate from, JanggiCoordinate to) {
        int rowDst = Math.abs(from.row() - to.row());
        int colDst = Math.abs(from.col() - to.col());

        if (square(rowDst) + square(colDst) != MA_REACHABLE_RADIUS) {
            throw new IllegalArgumentException("[ERROR] 마가 해당 위치로 이동할 수 없습니다.");
        }
    }

    private int square(int n) {
        return (int) Math.pow(n, 2);
    }
}
