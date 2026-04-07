package domain.strategy;

import domain.Position;
import domain.Piece;
import domain.constant.Palace;
import domain.constant.PieceType;
import java.util.List;

public class ChaMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        if (start.getRow() == end.getRow() || start.getCol() == end.getCol()) {
            return true;
        }
        Palace palace = Palace.from(piece.getCountry());
        return palace.isDiagonalPath(start, end);
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return pieces.isEmpty();
    }
}
