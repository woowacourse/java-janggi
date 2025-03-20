package domain;

import domain.position.Position;

public enum Team {
    HAN,
    CHO,
    ;

    public boolean isFront(Position before, Position after) {
        if (this == HAN) {
            return before.getY() < after.getY();
        }
        return before.getY() > after.getY();
    }
}
