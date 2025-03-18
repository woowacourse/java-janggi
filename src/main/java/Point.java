public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    private void validate(int x, int y) {
        if (x < 0 || x > 8) {
            throw new IllegalArgumentException("");
        }
        if (y < 0 || y > 9) {
            throw new IllegalArgumentException("");
        }
    }
}
