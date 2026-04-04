package participant;

import pieces.Side;

public enum Turn {

    CHO_TURN(Side.CHO),
    HAN_TURN(Side.HAN);

    private final Side side;

    Turn(Side side) {
        this.side = side;
    }

    public Turn other() {
        if (this == CHO_TURN) {
            return HAN_TURN;
        }
        return CHO_TURN;
    }

    public Side getSide() {
        return this.side;
    }

    public boolean isCho() {
        return this.side.isCho();
    }
}
