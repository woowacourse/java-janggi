package janggi.domain.board;

public class MoveResult {
    private final Position from;
    private final Position to;
    private final boolean captured;

    public MoveResult(Position from, Position to, boolean captured) {
        this.from = from;
        this.to = to;
        this.captured = captured;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public boolean isCaptured() {
        return captured;
    }
}
