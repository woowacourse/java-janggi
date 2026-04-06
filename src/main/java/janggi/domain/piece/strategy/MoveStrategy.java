package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.camp.CampType;
import janggi.domain.piece.PieceRule;

public interface MoveStrategy {

    void validate(Position source, Position destination, CampType campType, BoardChecker board, PieceRule pieceRule);
}
