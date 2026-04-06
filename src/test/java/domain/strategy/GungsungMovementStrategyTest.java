package domain.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.PathPieces;
import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.None;
import domain.piece.Piece;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class GungsungMovementStrategyTest {

    GungsungMovementStrategy gungsungMovementStrategy = new GungsungMovementStrategy();

    @Test
    void 궁성_내_이동이_가능하다() {
        Piece sourcePiece = new Jang(Team.HAN);
        Path path = new Path(new Position(1, 4), new Position(1, 5), List.of());

        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), new None());
        assertTrue(gungsungMovementStrategy.isValidPath(path, pathPieces));
    }

    @Test
    void 궁성_밖으로_나가면_이동이_불가능하다() {
        Piece sourcePiece = new Jang(Team.HAN);
        Path path = new Path(new Position(1, 4), new Position(3, 4), List.of());
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), new None());

        assertFalse(gungsungMovementStrategy.isValidPath(path, pathPieces));
    }

    @Test
    void 도착지에_우리팀_기물이_존재하면_이동이_불가능하다() {
        Piece sourcePiece = new Jang(Team.CHO);
        Piece destinationPiece = new Cha(Team.CHO);
        Path path = new Path(new Position(8, 4), new Position(8, 5), List.of());
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece);

        assertFalse(gungsungMovementStrategy.isValidPath(path, pathPieces));
    }

    @Test
    void 도착지에_다른팀_기물이_존재하면_이동이_가능하다() {
        Piece sourcePiece = new Jang(Team.CHO);
        Piece destinationPiece = new Cha(Team.HAN);
        Path path = new Path(new Position(8, 4), new Position(8, 5), List.of());
        PathPieces pathPieces = new PathPieces(sourcePiece, List.of(), destinationPiece);

        assertTrue(gungsungMovementStrategy.isValidPath(path, pathPieces));
    }
}
