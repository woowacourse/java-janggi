package janggi.domain.board;

import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceType;

public interface BoardChecker {

    boolean hasPieceAt(Position position);

    boolean isSameCampPieceAt(Position position, Camp camp);

    boolean hasSamePieceTypeAt(Position position, PieceType pieceType);
}
