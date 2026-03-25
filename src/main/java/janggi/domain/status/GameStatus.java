package janggi.domain.status;

import janggi.domain.Point;

public interface GameStatus {
    GameStatus move(Point from, Point to);
}
