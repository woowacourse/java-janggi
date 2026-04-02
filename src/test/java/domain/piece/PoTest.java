package domain.piece;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class PoTest {

    @Test
    void 포는_직선_이동_경로를_생성한다() {
        Piece po = new Po(Team.CHO);

        Path path = po.calculatePath(new Position(2, 0), new Position(2, 4));

        assertEquals(createPosition(2, 0), path.source());
        assertEquals(createPosition(2, 4), path.destination());
        assertEquals(List.of(createPosition(2, 1), createPosition(2, 2), createPosition(2, 3)), path.waypoints());
    }

    @Test
    void 포는_궁성_대각선_이동_경로를_생성한다() {
        Piece po = new Po(Team.CHO);

        Path path = po.calculatePath(new Position(0, 3), new Position(2, 5));

        assertEquals(createPosition(0, 3), path.source());
        assertEquals(createPosition(2, 5), path.destination());
        assertEquals(List.of(createPosition(1, 4)), path.waypoints());
    }
}

