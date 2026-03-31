package janggi.domain.board;

import janggi.domain.JanggiGame;
import janggi.domain.point.Point;

public interface BoardRepository {
    Long save(JanggiGame game);
    void update(Long roomId, Point from, Point to, JanggiGame game);
    JanggiGame loadGame(Long gameRoomId);
}
