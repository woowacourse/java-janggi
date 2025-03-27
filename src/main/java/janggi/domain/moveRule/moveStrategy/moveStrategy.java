package janggi.domain.moveRule.moveStrategy;

import janggi.domain.piece.Piece;
import java.util.List;

public interface moveStrategy {
    boolean canMoveAlongRoute(Piece piece, Piece destination, List<Piece> piecesInRoute);
}
