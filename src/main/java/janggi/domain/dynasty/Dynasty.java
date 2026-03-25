package janggi.domain.dynasty;

import janggi.domain.position.Direction;

public enum Dynasty {

    CHO(Direction.SOUTH),
    HAN(Direction.NORTH);

    private final Direction front;

    Dynasty(Direction front) {
        this.front = front;
    }

    public Direction front() {
        return front;
    }
    
}
