package domain.strategy;

import domain.constant.Palace;
import domain.constant.PieceType;
import domain.Position;
import domain.Piece;
import java.util.List;

public class PoMoveRule implements MoveRule {

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
        if (pieces.size() != 1) {
            return false;
        }

        if (pieces.getFirst().getPieceType().equals(PieceType.PO)) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }

        return !endPieceType.equals(PieceType.PO);
    }
}
