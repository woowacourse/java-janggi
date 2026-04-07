package repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.formation.FormationType;
import domain.coordination.Coordination;
import domain.game.JanggiGame;
import domain.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.Test;
import service.LoadedGame;

class GameSnapshotMapperTest {

    private final GameSnapshotMapper gameSnapshotMapper = new GameSnapshotMapper();

    @Test
    void 게임_상태를_스냅샷으로_저장하고_복원할_수_있다() {
        JanggiGame janggiGame = JanggiGame.of(FormationType.DEFAULT, FormationType.DEFAULT);
        janggiGame.playTurn(java.util.List.of(5, 7), java.util.List.of(5, 6));
        janggiGame.playTurn(java.util.List.of(5, 4), java.util.List.of(5, 5));

        LoadedGame restoredGame = gameSnapshotMapper.toLoadedGame(
                gameSnapshotMapper.from(new LoadedGame(1L, janggiGame))
        );

        assertThat(restoredGame.gameId()).isEqualTo(1L);
        assertThat(restoredGame.game().turn()).isEqualTo(janggiGame.turn());
        assertSameBoard(janggiGame.snapshot().board(), restoredGame.game().snapshot().board());
    }

    private void assertSameBoard(Map<Coordination, Piece> expected, Map<Coordination, Piece> actual) {
        assertThat(actual).hasSameSizeAs(expected);

        for (Map.Entry<Coordination, Piece> entry : expected.entrySet()) {
            Piece actualPiece = actual.get(entry.getKey());
            assertThat(actualPiece).isNotNull();
            assertThat(actualPiece.getClass()).isEqualTo(entry.getValue().getClass());
            assertThat(actualPiece.team()).isEqualTo(entry.getValue().team());
        }
    }
}
