package janggiGame.piece;

import janggiGame.board.Dot;
import java.util.List;
import java.util.Map;

public class King extends Piece {
    public King(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        validateRoute(dx, dy);

        return List.of();
    }

    @Override
    public void validateRoute(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new UnsupportedOperationException("[ERROR] 장이 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
