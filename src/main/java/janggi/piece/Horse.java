package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;

public final class Horse extends Piece {

    public Horse(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!((fromPoint.calculateXDistance(toPoint) == 2 && fromPoint.calculateYDistance(toPoint) == 1) ||
                (fromPoint.calculateXDistance(toPoint) == 1 && fromPoint.calculateYDistance(toPoint) == 2))) {
            throw new IllegalArgumentException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.HORSE;
    }
}
