package domain;

public class Soldier extends Piece {

    @Override
    boolean isMovable(BoardLocation current, BoardLocation target) {
        return false;
    }
}
