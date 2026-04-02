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

    @Test
    void 포는_궁성_밖_대각선_이동은_검증에_실패한다() {
        MovablePiece po = new Po(Team.CHO);
        Position source = new Position(6, 0);
        Position destination = new Position(4, 2);

        po.calculatePath(source, destination);
        PathPieces pathPieces = new PathPieces(
                po,
                List.of(new Cha(Team.HAN)),
                None.getInstance(),
                new MoveMeta(source.isInPalace(), destination.isInPalace(), true)
        );

        assertFalse(po.validatePath(pathPieces));
    }

    @Test
    void 포는_궁성_안_대각선_이동은_검증에_성공한다() {
        MovablePiece po = new Po(Team.CHO);
        Position source = new Position(0, 3);
        Position destination = new Position(2, 5);

        po.calculatePath(source, destination);
        PathPieces pathPieces = new PathPieces(
                po,
                List.of(new Cha(Team.HAN)),
                None.getInstance(),
                new MoveMeta(source.isInPalace(), destination.isInPalace(), true)
        );

        assertTrue(po.validatePath(pathPieces));
    }

    @Test
    void 포는_궁성_안_비연결_대각선_이동은_검증에_실패한다() {
        MovablePiece po = new Po(Team.CHO);
        PathPieces pathPieces = new PathPieces(
                po,
                List.of(new Cha(Team.HAN)),
                None.getInstance(),
                new MoveMeta(true, true, true, false)
        );

        assertFalse(po.validatePath(pathPieces));
    }
}
