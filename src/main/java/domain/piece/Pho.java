package domain.piece;

import domain.*;

public class Pho extends Piece implements LinearMove {

    public Pho(Country country) {
        super(country, PieceType.PHO);
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
        if (board.isOccupied(to) && isSameType(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
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

        curr = findFirstPieceCoordinate(janggiBoard, curr, to, direction);
        if (isSameType(janggiBoard.findPieceByCoordinate(curr))) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
        }
        if (curr.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 넘어야 공격할 수 있습니다.");
        }

        curr = findFirstPieceCoordinate(janggiBoard, curr.move(direction), to, direction);
        if (!curr.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 한번만 넘어 공격할 수 있습니다.");
        }
    }

    private JanggiCoordinate findFirstPieceCoordinate(JanggiBoard janggiBoard, JanggiCoordinate curr, JanggiCoordinate to, Direction direction) {
        while (!janggiBoard.isOccupied(curr) && !curr.equals(to)) {
            curr = curr.move(direction);
        }
        return curr;
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
