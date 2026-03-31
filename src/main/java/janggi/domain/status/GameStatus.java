package janggi.domain.status;

import janggi.domain.board.Board;
import janggi.domain.point.Point;

public interface GameStatus {
    Team getTeam();
    GameStatus move(Point from, Point to, Board board);

    default boolean isFinished() {
        return false;
    };
}
