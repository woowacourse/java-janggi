package janggi.data.dao.test;

import janggi.board.point.Point;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.piece.PieceSymbol;
import java.util.Set;

public class FakePiece extends Piece {

    public FakePiece(Camp camp) {
        super(camp);
    }

    @Override
    public Set<Point> findRoute(Point fromPoint, Point toPoint) {
        throw new UnsupportedOperationException("FakePiece는 findRoute를 구현하지 않습니다.");
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint, Set<Piece> piecesOnRoute) {
        throw new UnsupportedOperationException("FakePiece는 validateMove를 구현하지 않습니다.");
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return false;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return null;
    }

    @Override
    public int getPoint() {
        return 0;
    }
}
