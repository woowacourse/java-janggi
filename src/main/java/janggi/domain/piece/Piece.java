package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;

public interface Piece {

    boolean isSameDynasty(Dynasty dynasty);

    boolean isMovable(JanggiBoard janggiBoard, Point start, Point end);
}
