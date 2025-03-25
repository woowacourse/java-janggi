package domain.rule;

import domain.piece.Piece;
import java.util.List;

public interface MoveRule {
    boolean isValidateMoveRule(Piece piece, Piece destination, List<Piece> piecesInRoute);
}
