package janggi.domain.dynasty;

import janggi.domain.position.Direction;

public enum Dynasty {

    CHO(Direction.SOUTH) {
        @Override
        public Dynasty next() {
            return Dynasty.HAN;
        }
    },
    HAN(Direction.NORTH) {
        @Override
        public Dynasty next() {
            return Dynasty.CHO;
        }
    };

    private final Direction front;

    Dynasty(Direction front) {
        this.front = front;
    }

    public abstract Dynasty next();

    public Direction front() {
        return front;
    }

}
