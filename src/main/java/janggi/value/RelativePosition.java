package janggi.value;

import java.util.List;
import java.util.Objects;

public final class RelativePosition {

    private final int x;
    private final int y;

    public RelativePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public RelativePosition(Position start, Position end) {
        this.x = end.x() - start.x();
        this.y = end.y() - start.y();
    }

    public static RelativePosition calculateTotal(List<RelativePosition> others) {
        int newX = others.stream().mapToInt(position -> position.x).sum();
        int newY = others.stream().mapToInt(position -> position.y).sum();
        return new RelativePosition(newX, newY);
    }


    public RelativePosition calculateUnit() {
        int newX = Integer.compare(x, 0);
        int newY = Integer.compare(y, 0);
        return new RelativePosition(newX, newY);
    }

    public Position calculateAbsolutePosition(Position origin) {
        return origin.calculateSum(new Position(x, y));
    }

    @Override
    public String toString() {
        return "RelativePosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RelativePosition that = (RelativePosition) object;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
