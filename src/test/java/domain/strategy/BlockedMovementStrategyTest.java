package domain.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.PathPieces;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.None;
import domain.piece.Piece;
import domain.player.Team;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BlockedMovementStrategyTest {

    BlockedMovementStrategy blockedMovementStrategy = new BlockedMovementStrategy();

    @Test
    void 막히지_않은_길은_이동이_가능하다() {
        Piece sourcePiece = new Ma(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), new None());
        assertTrue(blockedMovementStrategy.isValidPath(null, pathPieces));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CHO", "HAN"})
    void 막힌_길은_이동이_불가능하다(Team team) {
        Piece sourcePiece = new Ma(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(team)), new None());
        assertFalse(blockedMovementStrategy.isValidPath(null, pathPieces));
    }

    @Test
    void 도착지에_우리팀_기물이_존재하면_이동이_불가능하다() {
        Piece sourcePiece = new Ma(Team.CHO);
        Piece destinationPiece = new Cha(Team.CHO);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece);

        assertFalse(blockedMovementStrategy.isValidPath(null, pathPieces));
    }

    @Test
    void 도착지에_다른팀_기물이_존재하면_이동이_가능하다() {
        Piece sourcePiece = new Ma(Team.CHO);
        Piece destinationPiece = new Cha(Team.HAN);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece);

        assertTrue(blockedMovementStrategy.isValidPath(null, pathPieces));
    }
}
