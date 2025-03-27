package janggi.piece;

import janggi.board.Board;
import janggi.board.Palace;
import janggi.board.point.Point;

public abstract class PalaceAffectedPiece extends Piece {

    public PalaceAffectedPiece(Camp camp, Board board) {
        super(camp, board);
    }

    public boolean isInsidePalace(Point point) {
        return Palace.isInsidePalace(point);
    }

    public boolean isDiagonalPalaceMove(Point fromPoint, Point toPoint) {
        return Palace.isDiagonalPalaceMoveAllowed(fromPoint, toPoint)
                && fromPoint.isDiagonal(toPoint);
    }
}
