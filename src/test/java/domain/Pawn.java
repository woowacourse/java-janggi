package domain;

import java.util.List;

public class Pawn {

    private final int cR;
    private final int cC;

    public Pawn(final int cR, final int cC) {
        this.cR = cR;
        this.cC = cC;
    }

    public List<int[]> getAvailablePositions() {
        return null;
    }
}
