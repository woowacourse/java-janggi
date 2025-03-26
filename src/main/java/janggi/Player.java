package janggi;

import janggi.piece.Side;

public class Player {
    private final Side side;
    private double score;

    public Player(Side side, double score) {
        this.side = side;
        this.score = score;
    }

    public static Player createRedSidePlayer() {
        return new Player(Side.RED, 73.5);
    }

    public static Player createBlueSidePlayer() {
        return new Player(Side.BLUE, 72);
    }

    public void minusScore(final int score) {
        this.score -= score;
    }

    public boolean isEnd() {
        return this.score <= 0;
    }

    public Side getSide() {
        return side;
    }

    public double getScore() {
        return score;
    }
}
