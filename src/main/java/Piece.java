public class Piece {

    private Position position;

    public Piece(int x, int y) {
        position = new Position(x, y);
    }

    public void move(int x, int y) {
        position = position.move(x, y);
    }

    public Position getPosition() {
        return position;
    }
}
