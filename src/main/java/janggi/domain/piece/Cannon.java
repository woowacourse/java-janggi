package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;

public class Cannon implements Piece {

    private final static Cannon CANNON = new Cannon();

    private Cannon() {
    }

    public static Cannon newInstance() {
        return CANNON;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }
}