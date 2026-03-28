package janggi.domain.piece;

import janggi.domain.MoveResult;
import janggi.domain.PieceInfo;
import janggi.domain.Side;
import janggi.domain.board.BoardInterface;
import janggi.domain.Position;

import java.util.List;

public interface Piece {
    List<Position> findRoute(Position start, Position end);

    void validateRoute(List<Position> path, BoardInterface boardInterface);

    boolean isPo();

    boolean isGung();

    boolean isEqualPieceType(PieceType pieceType);

    boolean isEqualSide(Side side);

    PieceInfo getPieceInfo();

    MoveResult capturedResult();
}
