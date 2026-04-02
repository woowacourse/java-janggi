package participant;

import pieces.Side;

public enum Turn {

    CHO_TURN(Side.CHO),
    HAN_TURN(Side.HAN);

    private final Side side;

    Turn(Side side) {
        this.side = side;
    }

    public Turn next() {
        if (this == CHO_TURN) {
            return HAN_TURN;
        }
        return CHO_TURN;
    }

    public Turn prev() {
        return next();
    }

    public Side getSide() {
        return this.side;
    }
}
