package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.piece.Piece;

import java.util.List;

public interface FormationStrategy {
    List<Piece> createPieces(Camp camp);
}
