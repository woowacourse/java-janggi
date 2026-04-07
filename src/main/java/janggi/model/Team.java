package janggi.model;

import janggi.model.position.absolute.Position;

public enum Team {
    HAN,
    CHO;

    public boolean isMovingBackward(Position from, Position to) {
        if (this == HAN) {
            return from.row().getValue() > to.row().getValue();
        }

        return from.row().getValue() < to.row().getValue();
    }
}
