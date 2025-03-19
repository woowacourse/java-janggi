package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;

public class Elephant implements Piece {

    private final static Elephant ELEPHANT = new Elephant();

    private Elephant() {
    }

    public static Elephant newInstance() {
        return ELEPHANT;
    }
    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }
}
