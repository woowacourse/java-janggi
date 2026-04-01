package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;

public interface Piece {
    Route findRoute(Position start, Position end);

    void validateRoute(Route route, BaseBoard boardInfo);

    boolean isEqualPieceType(PieceType pieceType);

    boolean isEqualSide(Side side);

    int getPieceScore();
    
    PieceAttribute getPieceInfo();
}
