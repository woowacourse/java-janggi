package domain.move.directions;

import domain.piece.Team;

public enum Vector {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),

    LEFT_UP(-1, -1),
    LEFT_DOWN(1, -1),
    RIGHT_UP(-1, 1),
    RIGHT_DOWN(1, 1),
    ;

    private static final int FORWARD_FOR_CHO = 0;
    private static final int FORWARD_FOR_HAN = 0;

    private final int dy;
    private final int dx;

    Vector(int dy, int dx) {
        this.dy = dy;
        this.dx = dx;
    }

    public int dy() {
        return dy;
    }

    public int dx() {
        return dx;
    }

    public boolean isForwardFor(Team team) {
        if (team == Team.CHO) {
            return this.dy <= FORWARD_FOR_CHO;
        }
        return this.dy >= FORWARD_FOR_HAN;
    }

}
