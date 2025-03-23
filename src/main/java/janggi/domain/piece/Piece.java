package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;

public interface Piece {

    boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Position start, Position end);
}
