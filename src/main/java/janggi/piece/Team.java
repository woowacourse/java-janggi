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

    @Override
    public String toString() {
        if (this == HAN) {
            return "한나라";
        }
        return "초나라";
    }
}
