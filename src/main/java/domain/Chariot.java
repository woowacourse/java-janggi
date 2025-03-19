package domain;

public class Chariot extends Piece {

    @Override
    boolean isMovable(BoardLocation current, BoardLocation target) {
        int differenceX = current.minusX(target);
        int differenceY = current.minusY(target);
        return differenceX == 0 || differenceY == 0;
    }
}
