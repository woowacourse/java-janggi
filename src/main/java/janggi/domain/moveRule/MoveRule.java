package janggi.domain.moveRule;

import janggi.domain.piece.Piece;
import java.util.List;

public interface MoveRule {
    boolean canMove(Piece piece, Piece destination, List<Piece> piecesInRoute);
}
