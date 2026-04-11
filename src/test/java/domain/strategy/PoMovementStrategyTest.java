package domain.strategy;

import domain.board.PathPieces;
import domain.piece.Cha;
import domain.piece.MovablePiece;
import domain.piece.Po;
import domain.player.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PoMovementStrategyTest {

    PoMovementStrategy poMovementStrategy = new PoMovementStrategy();

    @Test
    void 막히지_않은_경우_이동이_불가능하다() {
        MovablePiece sourcePiece = new Po(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of());
        assertFalse(poMovementStrategy.validatePath(pathPieces));
    }

    @ParameterizedTest
    @EnumSource(Team.class)
    void 포를_제외한_하나만_막혀있으면_이동이_가능하다(Team team) {
        MovablePiece sourcePiece = new Po(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(team)));
        assertTrue(poMovementStrategy.validatePath(pathPieces));
    }

    @ParameterizedTest
    @EnumSource(Team.class)
    void 포_하나만_막혀있으면_이동이_불가능하다(Team team) {
        MovablePiece sourcePiece = new Po(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Po(team)));
        assertFalse(poMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 목적지에_포가_아닌_같은_팀_기물이_있으면_이동이_불가능하다() {
        MovablePiece sourcePiece = new Po(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.CHO);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(Team.CHO)), destinationPiece);

        assertFalse(poMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 목적지에_포가_아닌_다른_팀_기물이_있으면_이동이_가능하다() {
        MovablePiece sourcePiece = new Po(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(Team.CHO)), destinationPiece);

        assertTrue(poMovementStrategy.validatePath(pathPieces));
    }

    @ParameterizedTest
    @EnumSource(Team.class)
    void 목적지에_포가_있으면_이동이_불가능하다(Team team) {
        MovablePiece sourcePiece = new Po(Team.CHO);
        MovablePiece destinationPiece = new Po(team);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(team)), destinationPiece);
        assertFalse(poMovementStrategy.validatePath(pathPieces));
    }
}

