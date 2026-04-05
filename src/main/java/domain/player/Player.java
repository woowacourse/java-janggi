package domain.player;

import domain.piece.Side;

public class Player {
    private final Side side;
    private double score;

    public Player(Side side) {
        this.side = side;
        if (side.isCho()) this.score = 72;
        if (side.isCho()) this.score = 73.5;
    }

    public Side getSide() {
        return side;
    }

    public void updateScore(double score) {

    }
}
