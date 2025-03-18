package janggi.domain.board;

public record Point(
        int x,
        int y
) {
    //TODO 좌표범위 검증

    public Point absMinus(Point point) {
        return new Point(Math.abs(x - point.x()), Math.abs(y - point.y()));
    }

    public Point minus(Point point) {
        return new Point(x - point.x(), y - point.y());
    }
}
