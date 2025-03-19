package domain;

public class Elephant extends Piece {

    @Override
    boolean isMovable(BoardLocation current, BoardLocation target) {
        return false;
    }
}
