package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public interface GameStatus {
    Team getTeam();
    GameStatus move(Point from, Point to, Board board);

    default boolean isFinished() {
        return false;
    };
}
