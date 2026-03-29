package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BoardInterface;

public interface Piece {
    Route findRoute(Position start, Position end);

    void validateRoute(Route route, BoardInterface boardInterface);

    boolean isEqualPieceType(PieceType pieceType);

    boolean isEqualSide(Side side);

    PieceInfo getPieceInfo();
}
