package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.point.Point;

public interface BoardInfo {
    boolean isEmpty(Point point);

    boolean isSamePiece(Point point, Piece piece);
}
