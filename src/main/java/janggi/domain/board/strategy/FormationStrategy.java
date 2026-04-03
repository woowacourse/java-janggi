package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.Piece;

import java.util.Map;

public interface FormationStrategy {
    Map<Position, Piece> createPieces(Camp camp);
}
