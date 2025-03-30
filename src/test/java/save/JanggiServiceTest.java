package save;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import move.JolMoveBehavior;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.PlayerPieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class JanggiServiceTest {

    private final JanggiPersistenceService janggiPersistenceService = new JanggiPersistenceService(
            new TestMySQLConnection());

    @AfterEach
    void clearDatabases() {
        janggiPersistenceService.resetJanggi();
    }

    @Test
    void 장기_턴_상황을_저장할_수_있다() {
        PlayerPieces playerPieces = new PlayerPieces(
                Map.of(Team.BLUE, new Pieces(
                                List.of(new Piece(new JanggiPosition(0, 0), new JolMoveBehavior(), Team.BLUE))),
                        Team.RED, new Pieces(
                                List.of(new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.RED)))
                ));
        janggiPersistenceService.saveJanggi(playerPieces, 1, Team.BLUE);
        Assertions.assertTrue(janggiPersistenceService.isPreviousGameExist());
    }

    @Test
    void 가장_최근_턴을_가져올_수_있다() {
        PlayerPieces playerPieces = new PlayerPieces(
                Map.of(Team.BLUE, new Pieces(
                                List.of(new Piece(new JanggiPosition(0, 0), new JolMoveBehavior(), Team.BLUE))),
                        Team.RED, new Pieces(
                                List.of(new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.RED)))
                ));
        janggiPersistenceService.saveJanggi(playerPieces, 1, Team.BLUE);
        Optional<Integer> previousTurn = janggiPersistenceService.findPreviousTurn();
        Assertions.assertEquals(previousTurn.get(), 1);
    }

    @Test
    void 가장_최근_턴의_피스들을_가지고_올_수_있다() {
        PlayerPieces playerPieces = new PlayerPieces(
                Map.of(Team.BLUE, new Pieces(
                                List.of(new Piece(new JanggiPosition(0, 0), new JolMoveBehavior(), Team.BLUE))),
                        Team.RED, new Pieces(
                                List.of(new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.RED)))
                ));
        janggiPersistenceService.saveJanggi(playerPieces, 1, Team.BLUE);
        Pieces pieces = janggiPersistenceService.loadPieces();
        Assertions.assertEquals(pieces.size(), 2);
    }
}
