package participant;

import pieces.Side;

public class HanTurn implements Turn {

    @Override
    public Turn move() {
        return new ChoTurn();
    }

    @Override
    public boolean isMatchSide(Side side) {
        return side == Side.HAN;
    }
}
