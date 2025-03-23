package janggi.value;

import java.util.Objects;

public final class RelativePosition {
    private final int x;
    private final int y;

    public RelativePosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RelativePosition relativePosition = (RelativePosition) object;
        return x == relativePosition.x && y == relativePosition.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
