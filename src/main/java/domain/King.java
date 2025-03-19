package domain;

public class King extends Piece{

    @Override
    boolean isMovable(BoardLocation current, BoardLocation target) {
        return false;
    }
}
