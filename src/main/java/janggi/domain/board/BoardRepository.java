package janggi.domain.board;

import janggi.domain.JanggiGame;
import janggi.domain.point.Point;
import java.sql.Connection;

public interface BoardRepository {
    long save(JanggiGame game, Connection connection);
    void update(long roomId, Point from, Point to, JanggiGame game, Connection connection);
    JanggiGame loadGame(long roomId, Connection connection);
}
