package domain;

public class Scholar extends Piece {

    @Override
    boolean isMovable(BoardLocation current, BoardLocation target) {
        return false;
    }
}
