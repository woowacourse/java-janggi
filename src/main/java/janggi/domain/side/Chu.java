package janggi.domain.side;

import janggi.domain.Pieces;
import janggi.dto.BoardSpot;
import java.util.List;

public class Chu implements Team {

    private final Pieces pieces;

    private Chu(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Chu createInitialChu() {
        return new Chu(Pieces.createChu());
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
        return new Chu(pieces.move(startX, startY, endX, endY));
    }

    // TODO: 구현하기
    public int calculateScore() {
        return 0;
    }
}
