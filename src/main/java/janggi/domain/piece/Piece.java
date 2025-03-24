package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;

public interface Piece {

    boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Point start, Point end);
}
