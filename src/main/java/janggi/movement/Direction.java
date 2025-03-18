package janggi.movement;

public class Direction {
    private final int offsetX;
    private final int offsetY;

    Direction(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public int getOffsetX(){
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
