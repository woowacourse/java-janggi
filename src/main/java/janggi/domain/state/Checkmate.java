package janggi.domain.state;

import janggi.domain.Camp;

public class Checkmate extends Finished {

    private final Camp camp;

    public Checkmate(Camp camp) {
        this.camp = camp;
    }

    @Override
    public Camp turn() {
        return camp;
    }

    @Override
    public String getStateType() {
        return "CHECKMATE";
    }
}
