package participant;

import pieces.Side;

public class ChoTurn implements Turn {

    @Override
    public Turn move() {
        return new HanTurn();
    }

    @Override
    public boolean isMatchSide(Side side) {
        return side == Side.CHO;
    }
}
