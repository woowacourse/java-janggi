package janggi.domain.board.point;

import janggi.domain.board.Direction;

public interface Point {
    Point move(Direction direction);

    boolean isSamePosition(Point point);

    int getX();

    int getY();

    Point copy(Point endPoint);
}
