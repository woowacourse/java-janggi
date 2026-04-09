package participant;

import pieces.Side;

public enum Turn {

    CHO_TURN(Side.CHO),
    HAN_TURN(Side.HAN),
    ;

    private final Side side;

    Turn(final Side side) {
        this.side = side;
    }

    public static Turn from(Side side) {
        if (side.isCho()) {
            return CHO_TURN;
        }
        return HAN_TURN;
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
