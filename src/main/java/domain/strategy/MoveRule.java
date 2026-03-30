package domain.strategy;

import domain.PieceType;
import domain.Position;
import domain.Piece;
import java.util.List;

public interface MoveRule {
    boolean canMovePosition(Position start, Position end, Piece piece);

    default boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return true;
    }
}
