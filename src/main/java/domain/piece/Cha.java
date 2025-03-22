package domain.piece;

import domain.Country;
import domain.JanggiCoordinate;
import domain.PieceType;
import domain.board.JanggiBoard;

public class Cha extends Piece implements LinearMove {

    protected Cha(Country country) {
        super(country, PieceType.CHA);
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

    private void validateRowCol(JanggiCoordinate from, JanggiCoordinate to) {
        if (!isSameRow(from, to) && !isSameCol(from, to)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean isSameRow(JanggiCoordinate from, JanggiCoordinate to) {
        return from.getRow() == to.getRow();
    }

    private boolean isSameCol(JanggiCoordinate from, JanggiCoordinate to) {
        return from.getCol() == to.getCol();
    }

    private void validateReachAble(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        Direction direction = getDirection(from, to);
        JanggiCoordinate curr = from.move(direction);
        while (!janggiBoard.isOccupied(curr) && !curr.equals(to)) {
            curr = curr.move(direction);
        }
        if (janggiBoard.isOccupied(curr) &&
                janggiBoard.findPieceByCoordinate(curr) != janggiBoard.findPieceByCoordinate(to)) {
            throw new IllegalArgumentException("[ERROR] 기물을 넘어서 이동 할 수 없습니다.");
        }
    }

    private Direction getDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (isSameRow(from, to)) {
            return getHorizontalDirection(from, to);
        }
        return getVerticalDirection(from, to);
    }

    private Direction getVerticalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.getRow() > to.getRow()) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    private Direction getHorizontalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.getCol() > to.getCol()) {
            return Direction.LEFT;
        }
        return Direction.RIGHT;
    }

    @Override
    public void validateLinearMove(JanggiBoard janggiBoard, JanggiCoordinate from, JanggiCoordinate to) {
        validateRowCol(from, to);
        validateReachAble(janggiBoard, from, to);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateMoveCoordinate(board, from, to);
        validateLinearMove(board, from, to);
        validateTarget(board, from, to);
    }
}
