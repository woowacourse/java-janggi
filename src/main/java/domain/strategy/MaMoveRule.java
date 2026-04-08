package domain.strategy;

import domain.Position;
import domain.Piece;
import domain.constant.PieceType;
import java.util.List;

public class MaMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffRow = end.getRow() - start.getRow();
        int diffCol = end.getCol() - start.getCol();

        return (Math.abs(diffRow) == 2 && Math.abs(diffCol) == 1) || (Math.abs(diffRow) == 1 && Math.abs(diffCol) == 2);
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return pieces.isEmpty();
    }
}
