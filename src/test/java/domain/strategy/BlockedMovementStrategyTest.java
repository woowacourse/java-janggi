package domain.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.PathPieces;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.MovablePiece;
import domain.player.Team;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.ParameterizedTest;

class BlockedMovementStrategyTest {

    BlockedMovementStrategy blockedMovementStrategy = new BlockedMovementStrategy();

    @Test
    void 막히지_않은_경우_이동이_가능하다() {
        MovablePiece sourcePiece = new Ma(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of());
        assertTrue(blockedMovementStrategy.validatePath(pathPieces));
    }

    @ParameterizedTest
    @EnumSource(Team.class)
    void 막혀있는_경우_이동이_불가능하다(Team team) {
        MovablePiece sourcePiece = new Ma(Team.CHO);

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(new Cha(team)));
        assertFalse(blockedMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 목적지에_같은_팀_기물이_있으면_이동이_불가능하다() {
        MovablePiece sourcePiece = new Ma(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.CHO);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece);

        assertFalse(blockedMovementStrategy.validatePath(pathPieces));
    }

    @Test
    void 목적지에_다른_팀_기물이_있으면_이동이_가능하다() {
        MovablePiece sourcePiece = new Ma(Team.CHO);
        MovablePiece destinationPiece = new Cha(Team.HAN);
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece);

        assertTrue(blockedMovementStrategy.validatePath(pathPieces));
    }
}

