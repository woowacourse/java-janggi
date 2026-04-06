package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.PieceRule;

public interface BoardChecker {

    boolean hasPieceAt(Position position);

    boolean hasSamePieceRuleAt(Position position, PieceRule pieceRule);
}
