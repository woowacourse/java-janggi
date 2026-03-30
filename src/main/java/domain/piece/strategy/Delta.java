package domain.piece.strategy;

public class Delta {
    private final int dx;
    private final int dy;

    public Delta(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}
