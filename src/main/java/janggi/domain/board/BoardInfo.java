package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.point.Point;

public interface BoardInfo {
    boolean isEmpty(Point point);

    boolean isSameType(Point point, Piece piece);

    boolean isOtherSide(Piece piece, Point point);

    boolean isInRange(Point point);
}
