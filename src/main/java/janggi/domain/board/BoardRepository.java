package janggi.domain.board;

import janggi.domain.JanggiGame;
import janggi.domain.point.Point;

public interface BoardRepository {
    long save(JanggiGame game);
    void update(long roomId, Point from, Point to, JanggiGame game);
    JanggiGame loadGame(long roomId);
}
