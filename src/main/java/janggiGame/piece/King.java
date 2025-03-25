package janggiGame.piece;

import janggiGame.Position;
import java.util.List;

public class King extends Piece {
    public King(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        int dx = origin.calculateRowChange(destination);
        int dy = origin.calculateColumnChange(destination);

        validateRoute(dx, dy);

        return List.of();
    }

    private void validateRoute(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new UnsupportedOperationException("[ERROR] 장이 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
