package janggi.piece;

import janggi.position.Direction;
import java.util.List;

public enum Team {
    HAN,
    CHO,
    ;

    public Team getOpposite() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public List<Direction> getTeamDirection() {
        if (this == CHO) {
            return Direction.getBackDirection();
        }
        return Direction.getFrontDirection();
    }
}
