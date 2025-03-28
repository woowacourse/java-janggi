package janggi.value;

import java.util.List;

public record RelativePosition(int x, int y) {

    public static RelativePosition calculateTotal(List<RelativePosition> others) {
        int newX = others.stream().mapToInt(position -> position.x).sum();
        int newY = others.stream().mapToInt(position -> position.y).sum();
        return new RelativePosition(newX, newY);
    }

    public Position calculateAbsolutePosition(Position origin) {
        return origin.calculateSum(new Position(x, y));
    }
}
