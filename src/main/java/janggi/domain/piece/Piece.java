package janggi.domain.piece;

import janggi.domain.MoveResult;
import janggi.domain.PieceInfo;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;
import janggi.domain.Position;

import java.util.List;

public interface Piece {
    List<Position> findRoute(Position start, Position end);

    void validateRoute(List<Position> path, BaseBoard baseBoard);

    boolean isEqualPieceType(PieceType pieceType);

    boolean isEqualSide(Side side);

    PieceInfo getPieceInfo();

    MoveResult capturedResult();
}
