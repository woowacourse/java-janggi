package application;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.game.JanggiGame;
import domain.piece.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GameReplayer 클래스 테스트")
class GameReplayerTest {

    @Test
    @DisplayName("명령 이력을 재생해 현재 게임 상태를 복원한다")
    void replayCommandsRestoresGameState() {
        GameReplayer gameReplayer = new GameReplayer();

        JanggiGame game = gameReplayer.replay(List.of("1", "1", "e6 e5"));

        assertThat(game.isPlayingPhase()).isTrue();
        assertThat(game.getTurn().getTeam()).isEqualTo(Team.HAN);
        assertThat(game.getBoard().isEmpty(new Position(Column.E, Row.SIX))).isTrue();
        assertThat(game.getBoard().findPieceByPosition(new Position(Column.E, Row.FIVE))).isPresent();
    }
}
