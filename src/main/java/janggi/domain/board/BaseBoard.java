package janggi.domain.board;

import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.ScoreStatus;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;

public interface BaseBoard {
    boolean isEmpty(Position position);

    boolean isEqualPieceType(Position position, PieceType pieceType);

    boolean isAlly(Side side, Position position);

    PieceInfo[][] getCurrentBoard();

    ScoreStatus getScoreStatus();
}
