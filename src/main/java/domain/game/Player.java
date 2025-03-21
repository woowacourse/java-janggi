package domain.game;

import domain.piece.Side;

public class Player {
    private Side side;

    public Player() {
        this.side = Side.CHO;
    }

    public Side getSide() {
        return side;
    }

    public void change() {
        this.side = Side.HAN;
    }
}
