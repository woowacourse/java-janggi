package janggi.domain;

import janggi.domain.rule.route.Direction;

// TODO 다형성 적용 고려해보기
public enum Side {

    HAN,
    CHO;

    public Side switchTurn() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public Direction getBackDirection() {
        if(this == HAN) {
            return Direction.BACK;
        }
        return Direction.FRONT;
    }
}
