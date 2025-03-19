package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;

public class Rook implements Piece {

    private final static Rook ROOK = new Rook();

    private Rook() {
    }

    public static Rook newInstance() {
        return ROOK;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }
}
