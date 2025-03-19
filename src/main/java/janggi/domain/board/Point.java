package janggi.domain.board;

public interface Point {
    Point move(Direction direction);

    boolean isSamePosition(Point point);

    int getX();

    int getY();
}
