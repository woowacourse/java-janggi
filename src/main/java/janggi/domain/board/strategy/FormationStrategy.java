package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.Map;

public interface FormationStrategy {
    Map<Position, Piece> createPieces(Camp camp);
}
