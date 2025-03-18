public class General {

    private static final int MAXIMUM_MOVEMENT_LIMIT = 1;

    private Position position;

    public General(int x, int y) {
        position = new Position(x, y);
    }

    public void move(int x, int y) {
        validateMoveRange(x, y);
        this.position = new Position(x, y);
    }

    public Position position() {
        return position;
    }

    private void validateMoveRange(int x, int y) {
        validateHorizontalRange(x);
        validateVerticalRange(y);
    }

    private void validateHorizontalRange(int x) {
        if(position.x() - MAXIMUM_MOVEMENT_LIMIT > x || position.x() + MAXIMUM_MOVEMENT_LIMIT < x) {
            throw new IllegalArgumentException();
        }
    }

    private void validateVerticalRange(int y) {
        if (position.y() + MAXIMUM_MOVEMENT_LIMIT < y || position.y() - MAXIMUM_MOVEMENT_LIMIT > y) {
            throw new IllegalArgumentException();
        }
    }
}
