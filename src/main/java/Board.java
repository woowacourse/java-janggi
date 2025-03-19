public class Board {

    private static final int WIDTH_SIZE = 9;
    private static final int HEIGHT_SIZE = 10;

    public boolean isInboard(Position position) {
        return position.x() < WIDTH_SIZE && position.x() >= 0
            && position.y() < HEIGHT_SIZE && position.y() >= 0;
    }
}
