package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;

public class Cannon implements Piece {
    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return false;
    }
}