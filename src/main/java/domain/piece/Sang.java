package domain.piece;

import domain.*;

public class Sang extends Piece {

    private static final int SANG_REACHABLE_RADIUS = 13;

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateMaMoveStrategy(board, from, to);
        validateTarget(board, to);
    }

    private void validateMaMoveStrategy(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to);
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
        return Direction.LEFT_DONW;
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    private void validateReachableCoordinate(JanggiCoordinate from, JanggiCoordinate to) {
        int rowDst = Math.abs(from.row() - to.row());
        int colDst = Math.abs(from.col() - to.col());

        if (square(rowDst) + square(colDst) != SANG_REACHABLE_RADIUS) {
            throw new IllegalArgumentException("[ERROR] 상이 해당 위치로 이동할 수 없습니다.");
        }
    }

    private int square(int n) {
        return (int) Math.pow(n, 2);
    }
}
