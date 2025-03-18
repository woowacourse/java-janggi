public class General {

    private Position position;

    public General(int x, int y) {
        position = new Position(x, y);
    }

    public void move(int x, int y) {
        if (position.y() + 2 <= y) {
            throw new IllegalArgumentException();
        }
        this.position = new Position(x, y);
    }

    public Position position() {
        return position;
    }
}
