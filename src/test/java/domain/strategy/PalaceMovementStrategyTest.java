package domain.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.PathPieces;
import domain.board.MoveMeta;
import domain.piece.Cha;
import domain.piece.MovablePiece;
import domain.player.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class PalaceMovementStrategyTest {

    PalaceMovementStrategy palaceMovementStrategy = new PalaceMovementStrategy();

    @Test
    void 출발지_도착지_모두_궁성내_이동이_가능하다() {
        MovablePiece sourcePiece = new Cha(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);

        MoveMeta moveMeta = new MoveMeta(true, true, false);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece, moveMeta);
        assertTrue(palaceMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 출발지_궁성내_도착지_궁성밖_이동이_불가능하다() {
        MovablePiece sourcePiece = new Cha(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);

        MoveMeta moveMeta = new MoveMeta(true, false, false);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece, moveMeta);
        assertFalse(palaceMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 출발지_궁성밖_도착지_궁성내_이동이_불가능하다() {
        MovablePiece sourcePiece = new Cha(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);

        MoveMeta moveMeta = new MoveMeta(false, true, false);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece, moveMeta);
        assertFalse(palaceMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 출발지_도착지_모두_궁성밖_이동이_불가능하다() {
        MovablePiece sourcePiece = new Cha(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);

        MoveMeta moveMeta = new MoveMeta(false, false, false);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece, moveMeta);
        assertFalse(palaceMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 출발지_도착지_모두_궁성내이지만_같은_팀_기물이_목적지에_있으면_이동이_불가능하다() {
        MovablePiece sourcePiece = new Cha(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.CHO);

        MoveMeta moveMeta = new MoveMeta(true, true, false);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece, moveMeta);
        assertFalse(palaceMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 궁성_내_직선_이동은_palaceDiagonalReachable이_false여도_가능하다() {
        MovablePiece sourcePiece = new Cha(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);

        MoveMeta moveMeta = new MoveMeta(true, true, false, false);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece, moveMeta);
        assertTrue(palaceMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 궁성_내_비연결_대각선_이동은_불가능하다() {
        MovablePiece sourcePiece = new Cha(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);

        MoveMeta moveMeta = new MoveMeta(true, true, true, false);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece, moveMeta);
        assertFalse(palaceMovementStrategy.validatePath(pathPieces));
    }
}
