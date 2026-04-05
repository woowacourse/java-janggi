package janggi.domain.board;

import janggi.domain.JanggiGame;
import janggi.domain.point.Point;
import java.util.Optional;

public interface BoardRepository {
    long save(JanggiGame game);
    void update(long roomId, Point from, Point to, JanggiGame game);
    Optional<JanggiGame> findJanggiGameByRoomId(long roomId);

    default JanggiGame loadGame(long roomId) {
        return findJanggiGameByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 게임방 입니다."));
    }
}
