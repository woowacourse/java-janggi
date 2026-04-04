package janggi.domain.status;

import janggi.domain.Boards;
import janggi.domain.Point;

public interface GameStatus {
    Team getTeam();
    GameStatus move(Point from, Point to, Boards boards);

    default boolean isFinished() {
        return false;
    };
}
