package janggi.position;

import java.util.Objects;

public class Position {
    private final PositionX x;
    private final PositionY y;

    public Position(int x, int y) {
        this.x = new PositionX(x);
        this.y = new PositionY(y);
    }

    public int getX() {
        return x.getValue();
    }

    public int getY() {
        return y.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) && Objects.equals(y, position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
