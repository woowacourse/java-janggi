public class General {

    private static final int MAXIMUM_MOVEMENT_LIMIT = 1;

    private Position position;

    public General(int x, int y) {
        position = new Position(x, y);
    }

    public void move(int x, int y) {
        validateVerticalRange(y);
        if(position.x() - 1 > x) {
            throw new IllegalArgumentException();
        }
        this.position = new Position(x, y);
    }

    private void validateVerticalRange(int y) {
        if (position.y() + MAXIMUM_MOVEMENT_LIMIT < y || position.y() - MAXIMUM_MOVEMENT_LIMIT > y) {
            throw new IllegalArgumentException();
        }
    }

    public Position position() {
        return position;
    }
}
