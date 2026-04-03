package domain.strategy;

import domain.constant.Palace;
import domain.constant.PieceType;
import domain.Position;
import domain.Piece;
import java.util.List;

public class PoMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        if (start.getX() == end.getX() || start.getY() == end.getY()) {
            return true;
        }
        Palace palace = Palace.from(piece.getCountry());
        return palace.isDiagonalPath(start, end);
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        // 중간에 기물 하나인지 && 넘는게 포인지
        if (countSameLine(pieces) != 1) {
            return false;
        }

        // 도착 기물이 포인지
        return !endPieceType.equals(PieceType.PO);
    }

    private int countSameLine(List<Piece> pieces) {
        int pieceCount = 0;
        for (Piece piece : pieces) {
            if (piece.getPieceType().equals(PieceType.PO)) {
                throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
            }

            if (!piece.equals(Piece.getEmptyPiece())) {
                pieceCount++;
            }
        }

        return pieceCount;
    }
}
