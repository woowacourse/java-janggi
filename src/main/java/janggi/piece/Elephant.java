package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;

public final class Elephant extends Piece {

    public Elephant(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!((fromPoint.calculateXDistance(toPoint) == 2 && fromPoint.calculateYDistance(toPoint) == 3) ||
                (fromPoint.calculateXDistance(toPoint) == 3 && fromPoint.calculateYDistance(toPoint) == 2))) {
            throw new IllegalArgumentException("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.ELEPHANT;
    }
}
