package game;

import java.util.Objects;

public class Dot {
    private final Integer x;
    private final Integer y;

    public Dot(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public int getDx(Dot other) {
        return other.x - this.x;
    }

    public int getDy(Dot other) {
        return other.y - this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;
        return Objects.equals(x, dot.x) && Objects.equals(y, dot.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
