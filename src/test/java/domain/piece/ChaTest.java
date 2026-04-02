package domain.piece;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.MoveMeta;
import domain.board.PathPieces;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChaTest {

    @Test
    void 차는_궁성_대각선_경로를_생성한다() {
        Piece cha = new Cha(Team.CHO);

        Path path = cha.calculatePath(new Position(0, 3), new Position(2, 5));

        assertEquals(createPosition(0, 3), path.source());
        assertEquals(createPosition(2, 5), path.destination());
        assertEquals(List.of(createPosition(1, 4)), path.waypoints());
    }

    @Test
    void 차는_직선_이동_경로를_생성한다() {
        Piece cha = new Cha(Team.CHO);

        Path path = cha.calculatePath(new Position(2, 0), new Position(2, 3));

        assertEquals(createPosition(2, 0), path.source());
        assertEquals(createPosition(2, 3), path.destination());
        assertEquals(List.of(createPosition(2, 1), createPosition(2, 2)), path.waypoints());
    }

    @Test
    void 차는_궁성_밖_대각선_이동_검증에_실패한다() {
        MovablePiece cha = new Cha(Team.CHO);
        PathPieces pathPieces = new PathPieces(
                cha,
                List.of(),
                None.getInstance(),
                new MoveMeta(false, false, true)
        );

        assertFalse(cha.validatePath(pathPieces));
    }

    @Test
    void 차는_궁성_안_대각선_이동_검증에_성공한다() {
        MovablePiece cha = new Cha(Team.CHO);
        PathPieces pathPieces = new PathPieces(
                cha,
                List.of(),
                None.getInstance(),
                new MoveMeta(true, true, true)
        );

        assertTrue(cha.validatePath(pathPieces));
    }
}
