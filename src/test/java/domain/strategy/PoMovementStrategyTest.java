package domain.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.PathPieces;
import domain.piece.Cha;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.Po;
import domain.player.Team;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PoMovementStrategyTest {

    PoMovementStrategy poMovementStrategy = new PoMovementStrategy();

    @Test
    void 막히지_않은_길은_이동이_불가능하다() {
        Piece sourcePiece = new Po(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), new None());
        assertFalse(poMovementStrategy.isValidPath(null, pathPieces));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CHO", "HAN"})
    void 포를_제외한_하나만_막힌_길은_이동이_가능하다(Team team) {
        Piece sourcePiece = new Po(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(team)), new None());
        assertTrue(poMovementStrategy.isValidPath(null, pathPieces));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CHO", "HAN"})
    void 포인_하나만_막힌_길은_이동이_불가능하다(Team team) {
        Piece sourcePiece = new Po(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Po(team)), new None());
        assertFalse(poMovementStrategy.isValidPath(null, pathPieces));
    }

    @Test
    void 도착지에_포가_아닌_우리팀_기물이_존재하면_이동이_불가능하다() {
        Piece sourcePiece = new Po(Team.CHO);
        Piece destinationPiece = new Cha(Team.CHO);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(Team.CHO)), destinationPiece);

        assertFalse(poMovementStrategy.isValidPath(null, pathPieces));
    }

    @Test
    void 도착지에_포가_아닌_다른팀_기물이_존재하면_이동이_불가능하다() {
        Piece sourcePiece = new Po(Team.CHO);
        Piece destinationPiece = new Cha(Team.HAN);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(Team.CHO)), destinationPiece);

        assertTrue(poMovementStrategy.isValidPath(null, pathPieces));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CHO", "HAN"})
    void 도착지에_포_기물이_존재하면_이동이_불가능하다(Team team) {
        Piece sourcePiece = new Po(Team.CHO);
        Piece destinationPiece = new Po(team);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(team)), destinationPiece);
        assertFalse(poMovementStrategy.isValidPath(null, pathPieces));
    }
}
