package janggi.domain.side;

import janggi.domain.Pieces;

public class Chu implements Team {

    private final Pieces pieces;
    private final int score;

    private Chu(Pieces pieces, int score) {
        this.pieces = pieces;
        this.score = score;
    }

    public static Chu createInitialChu() {
        return new Chu(Pieces.createChu(), 0);
    }

    @Override
    public boolean isPieceExists(int x, int y) {
        return pieces.isPieceExists(x, y);
    }

    @Override
    public Team move(int startX, int startY, int endX, int endY) {
        return new Chu(pieces.move(startX, startY, endX, endY), calculateScore());
    }

    // TODO: 구현하기
    public int calculateScore() {
        return 0;
    }
}
