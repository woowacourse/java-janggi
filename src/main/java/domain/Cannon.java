package domain;

public class Cannon extends Piece {


    @Override
    boolean isMovable(BoardLocation current, BoardLocation target) {
        return false;
    }
}
