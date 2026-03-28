package participant;

import pieces.Side;

public class HanTurn implements Turn {

    @Override
    public Turn move() {
        return new ChoTurn();
    }

    @Override
    public Side side() {
        return Side.HAN;
    }
}
