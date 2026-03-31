package janggi.domain.game;

import janggi.domain.board.Direction;
import java.util.EnumSet;

public enum Side {
    CHO("초", Direction.N),
    HAN("한", Direction.S),
    ;

    private final String displayName;
    private final Direction forward;

    Side(String displayName, Direction forward) {
        this.displayName = displayName;
        this.forward = forward;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Side opposite() {
        if (this.equals(CHO)) {
            return HAN;
        }
        return CHO;
    }

    public EnumSet<Direction> getSoldierDirections() {
        return EnumSet.of(this.forward, Direction.E, Direction.W);
    }
}
