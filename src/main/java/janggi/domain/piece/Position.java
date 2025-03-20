package janggi.domain.piece;

public record Position(int x, int y) {

    public Position {
        validatePosition(x, y);
    }

    public Position plus(Position other) {
        return new Position(other.x() + x, other.y() + y);
    }

    public Position plus(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    private void validatePosition(int x, int y) {
        if(x <= 0 || x > 10 || y <= 0 || y > 9) {
            throw new IllegalArgumentException();
        }
    }
}
