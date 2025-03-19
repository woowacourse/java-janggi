package domain;

public class Horse extends Piece {

    @Override
    boolean isMovable(BoardLocation current, BoardLocation target) {
        return false;
    }
}
