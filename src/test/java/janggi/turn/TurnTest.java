package janggi.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.players.Team;
import janggi.piece.players.Turn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void Turn을_생성한다() {
        // Given

        // When & Then
        Assertions.assertThatCode(() -> new Turn(Team.HAN, false, false))
                .doesNotThrowAnyException();
    }

    @Test
    void Turn을_초나라_팀으로_초기화한다() {
        // Given
        final Turn turn = Turn.initialize(Team.CHO);

        // When & Then
        assertThat(turn.getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 다음_턴으로_이동한다() {
        // Given
        final Turn turn = Turn.initialize(Team.CHO);

        // When & Then
        assertThat(turn.moveNextTurn().getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 두_팀_모두_종료를_원하는지_확인한다() {
        // Given
        Turn turn = Turn.initialize(Team.CHO);
        turn.wantExit();
        turn = turn.moveNextTurn();
        turn.wantExit();

        // When & Then
        assertThat(turn.canExit()).isTrue();
    }

    @Test
    void 한_팀이라도_종료를_원하지_않으면_게임은_계속된다() {
        // Given
        Turn turn = Turn.initialize(Team.CHO);
        turn.wantExit();

        // When & Then
        assertThat(turn.canExit()).isFalse();
    }
}
