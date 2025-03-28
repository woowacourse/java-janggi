package janggi.value;

public record RelativePosition(int x, int y) {

    public Position covertAbsolutePosition(Position origin) {
        return origin.calculateSum(new Position(x, y));
    }
}
