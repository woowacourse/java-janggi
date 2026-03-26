package model;

public class Move {
    private final Position from;
    private final Position to;

    private Move(Position from, Position to) {
        validate(from, to);
        this.from = from;
        this.to = to;
    }

    public static Move of(Position from, Position to) {
        return new Move(from, to);
    }

    private void validate(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("같은 위치로는 이동할 수 없습니다.");
        }
    }
}
