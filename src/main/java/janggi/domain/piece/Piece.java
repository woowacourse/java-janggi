package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BoardInfo;

public interface Piece {
    Route findRoute(Position start, Position end);

    void validateRoute(Route route, BoardInfo boardInfo);

    boolean isEqualPieceType(PieceType pieceType);

    boolean isEqualSide(Side side);

    PieceInfo getPieceInfo();
}
