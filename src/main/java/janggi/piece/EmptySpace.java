package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;

public final class EmptySpace extends Piece {

    public EmptySpace(Board board) {
        super(Camp.NEUTRAL, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
    }

    @Override
    public void validateCatch(Piece otherPiece) {
        throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
    }

    @Override
    public void validateSelect(Camp baseCamp) {
        throw new IllegalArgumentException("해당 위치에서 기물을 찾을 수 없습니다.");
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.EMPTY_SPACE;
    }

    @Override
    public boolean exists() {
        return false;
    }
}
