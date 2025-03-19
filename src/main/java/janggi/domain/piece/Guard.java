package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;

public class Guard implements Piece {

    private final static Guard GUARD = new Guard();

    private Guard() {
    }

    public static Guard newInstance() {
        return GUARD;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }
}
