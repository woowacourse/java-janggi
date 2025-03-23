package domain.piece;

import domain.*;

public class Cha extends Piece implements LinearMove {

    public Cha(Country country) {
        super(country, PieceType.CHA);
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
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
        return from.row() == to.row();
    }

    private boolean isSameCol(JanggiCoordinate from, JanggiCoordinate to) {
        return from.col() == to.col();
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
        if (from.row() > to.row()) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    private Direction getHorizontalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.col() > to.col()) {
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
        validateLinearMove(board, from, to);
        validateTarget(board, to);
    }
}
