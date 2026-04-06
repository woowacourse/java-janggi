package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;

public interface MoveStrategy {

    void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceRule pieceRule);
}
