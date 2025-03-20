package janggi.domain.piece;

public record Distance(
        int x, int y
) {
    private static final Distance UP = new Distance(-1, 0);
    private static final Distance DOWN = new Distance(1, 0);
    private static final Distance LEFT = new Distance(0, -1);
    private static final Distance RIGHT = new Distance(0, 1);

    public static Distance getDistance(int relativeX, int relativeY) {
        if(relativeX < 0) {
            return UP;
        }
        if(relativeX > 0) {
            return DOWN;
        }
        if(relativeY < 0) {
            return LEFT;
        }
        if(relativeY > 0) {
            return RIGHT;
        }
        throw new IllegalArgumentException();
    }


}
