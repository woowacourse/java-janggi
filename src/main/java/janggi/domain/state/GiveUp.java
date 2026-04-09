package janggi.domain.state;

import janggi.domain.Camp;

public class GiveUp extends Finished {

    private final Camp camp;

    public GiveUp(Camp camp) {
        this.camp = camp;
    }

    @Override
    public Camp turn() {
        return camp;
    }
}
