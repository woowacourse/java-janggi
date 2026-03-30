package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;

public interface BoardInfo {
    boolean isEmpty(Position position);

    boolean isEqualPieceType(Position position, PieceType pieceType);

    boolean isAlly(Side side, Position position);
}
