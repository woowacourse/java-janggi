package domain.player;

import domain.piece.Side;

public class Player {
    private final Side side;

    private Player(Side side) {
        this.side = side;
    }

    public static Player of(Side side) {
        return new Player(side);
    }

    public Side getSide() {
        return side;
    }
}
