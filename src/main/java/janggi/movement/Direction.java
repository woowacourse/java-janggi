package janggi.movement;

import janggi.position.Position;

public class Direction {
    private final int offsetX;
    private final int offsetY;

    Direction(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

//    public boolean checkOutOfBounds(Position newPosition) {
//
//    }

    public Position plusOffsetToPosition(Position position) {
        return position.applyOffsetToPosition(offsetX, offsetY);
    }

    public int getOffsetX(){
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
