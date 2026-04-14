package janggi.domain.state;

import janggi.domain.Camp;

public class Draw extends Finished {

    private final Camp camp;

    public Draw(Camp camp) {
        this.camp = camp;
    }

    @Override
    public Camp turn() {
        return camp;
    }

    @Override
    public String getStateType() {
        return "DRAW";
    }
}
