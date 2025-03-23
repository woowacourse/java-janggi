package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Point;
import java.util.List;

public interface Piece {

    boolean isEmptyPiece();

    List<Point> movePath(Point from, Point to);

    boolean canMove(PiecesOnPath piecesOnPath);

    boolean isDynasty(Dynasty dynasty);

    boolean isSamePiece(Piece piece);
}

