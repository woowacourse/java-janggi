package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;

public class King implements Piece {

    private final static King KING = new King();

    private King() {
    }

    public static King newInstance() {
        return KING;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }
}
