package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public abstract class PalaceRestrictedPiece extends PalaceAffectedPiece {

    public PalaceRestrictedPiece(Camp camp, Board board) {
        super(camp, board);
    }

    public boolean isBothOutsidePalace(Point fromPoint, Point toPoint) {
        return !isInsidePalace(fromPoint) || !isInsidePalace(toPoint);
    }
}
