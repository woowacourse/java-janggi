package janggi.model;

import janggi.model.position.absolute.Position;

public enum Team {
    HAN,
    CHO;

    public boolean isMovingBackward(Position from, Position to) {
        boolean locatedNorth = from.isLocatedNorthOf(to);

        if (this == HAN) {
            return !locatedNorth;
        }

        return locatedNorth;
    }
}
