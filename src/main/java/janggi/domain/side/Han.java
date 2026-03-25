package janggi.domain.side;

import janggi.domain.Pieces;
import janggi.dto.BoardSpot;
import java.util.List;

public class Han implements Team {

    private final Pieces pieces;

    private Han(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Han createInitialHan() {
        return new Han(Pieces.createHan());
    }

    @Override
    public boolean isPieceExists(int x, int y) {
        return pieces.isPieceExists(x, y);
    }

    @Override
    public List<BoardSpot> makeSpots() {
        return pieces.makeSpots();
    }

    @Override
    public Team move(int startX, int startY, int endX, int endY) {
        return null;
    }

    // TODO: 구현하기
    public int calculateScore() {
        return 0;
    }
}
