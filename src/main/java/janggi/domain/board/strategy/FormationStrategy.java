package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.piece.Piece;

import java.util.Map;

public interface FormationStrategy {
    Map<JanggiPosition, Piece> createPieces(Camp camp);
}
