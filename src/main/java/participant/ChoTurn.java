package participant;

import pieces.Side;

public class ChoTurn implements Turn {

    @Override
    public Turn move() {
        return new HanTurn();
    }

    @Override
    public Side side() {
        return Side.CHO;
    }
}
