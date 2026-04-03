package janggi.domain.board;

public enum BoardDirection {
    UP(1),
    DOWN(-1);

    private final int direction;

    BoardDirection(final int direction) {
        this.direction = direction;
    }

    public boolean isForward(int direction) {
        return this.direction == direction;
    }
}
