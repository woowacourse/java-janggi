package janggi.domain.piece;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;

public interface Piece {

    boolean isMovable(JanggiBoard janggiBoard, Point start, Point end);
}
