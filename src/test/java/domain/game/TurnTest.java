package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 게임은_초_진영부터_시작한다() {
        Turn turn = Turn.first();
        assertThat(turn.isTurn(Team.CHO)).isTrue();
    }

    @Test
    void 초_진영_다음_턴은_한_진영이다() {
        Turn turn = Turn.first();
        assertThat(turn.next().isTurn(Team.HAN)).isTrue();
    }

    @Test
    void 한_진영_다음_턴은_초_진영이다() {
        Turn turn = Turn.first().next();
        assertThat(turn.next().isTurn(Team.CHO)).isTrue();
    }

    @Test
    void 현재_팀의_턴이면_true를_반환한다() {
        Turn turn = Turn.first();
        assertThat(turn.isTurn(Team.CHO)).isTrue();
    }

    @Test
    void 현재_팀이_아니면_false를_반환한다() {
        Turn turn = Turn.first();
        assertThat(turn.isTurn(Team.HAN)).isFalse();
    }
}
