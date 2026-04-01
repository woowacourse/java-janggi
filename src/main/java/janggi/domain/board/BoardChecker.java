package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;

public interface BoardChecker {

    boolean hasPieceAt(Position position);

    boolean hasSameCampPieceAt(Position position, Camp camp);

    boolean hasSamePieceRuleAt(Position position, PieceRule pieceRule);
}
