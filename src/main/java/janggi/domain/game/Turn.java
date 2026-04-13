package janggi.domain.game;

import java.util.Objects;

public class Turn {
    private final Side current;

    private Turn(Side side) {
        this.current = side;
    }

    public static Turn from(Side side) {
        return new Turn(side);
    }

    public Turn switchTurn() {
        return new Turn(current.opposite());
    }

    public boolean isCurrent(Side side) {
        return this.current == side;
    }

    public Side getSide() {
        return current;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Turn turn = (Turn) o;
        return current == turn.current;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(current);
    }
}
