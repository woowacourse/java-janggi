package janggi.domain.side;

import janggi.domain.Pieces;

public class Han implements Team {

    private final Pieces pieces;
    private final int score;

    private Han(Pieces pieces, int score) {
        this.pieces = pieces;
        this.score = score;
    }

    public static Han createInitialHan() {
        return new Han(Pieces.createHan(), 0);
    }

    @Override
    public boolean isPieceExists(int x, int y) {
        return pieces.isPieceExists(x, y);
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
