package janggiGame.piece;

import janggiGame.board.Dot;
import java.util.List;

public class Advisor extends Piece {
    public Advisor(Dynasty dynasty) {
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
            throw new UnsupportedOperationException("[ERROR] 사가 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.ADVISOR;
    }
}
