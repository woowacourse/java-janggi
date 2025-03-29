package janggi.domain.piece.move;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.Piece;

public interface MoveStrategy {

    boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end);
}
