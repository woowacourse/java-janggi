package domain.piece;

import domain.Country;
import domain.JanggiCoordinate;
import domain.PieceType;
import domain.board.JanggiBoard;

public class Sang extends Piece {

    private static final int SANG_REACHABLE_RADIUS = 13;

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateMoveCoordinate(board, from, to);
        validateMaMoveStrategy(board, from, to);
        validateTarget(board, from, to);
    }

    private void validateMaMoveStrategy(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to);
        validateDoesNotHasObstacle(board, from, to);
    }

    private void validateDoesNotHasObstacle(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        Direction moveDirection = getDirection(from, to);
        if (moveDirection == Direction.UP && board.isOccupied(from.moveUp())) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
        }
        if (moveDirection == Direction.RIGHT && board.isOccupied(from.moveRight())) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
        }
        if (moveDirection == Direction.DOWN && board.isOccupied(from.moveDown())) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 있어 해당 위치로 이동할 수 없습니다.");
        }
        if (moveDirection == Direction.LEFT && board.isOccupied(from.moveLeft())) {
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

    private void validateMoveCoordinate(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (board.isOutOfBoundary(from) || board.isOutOfBoundary(to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 좌표위치 입니다.");
        }
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    private void validateReachableCoordinate(JanggiCoordinate from, JanggiCoordinate to) {
        int rowDst = Math.abs(from.getRow() - to.getRow());
        int colDst = Math.abs(from.getCol() - to.getCol());

        if (square(rowDst) + square(colDst) != SANG_REACHABLE_RADIUS) {
            throw new IllegalArgumentException("[ERROR] 상이 해당 위치로 이동할 수 없습니다.");
        }
    }

    private int square(int n) {
        return (int) Math.pow(n, 2);
    }
}
