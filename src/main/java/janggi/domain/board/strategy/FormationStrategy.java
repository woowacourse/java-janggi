package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public interface FormationStrategy {
    List<Piece> createPieces(Camp camp);
}
