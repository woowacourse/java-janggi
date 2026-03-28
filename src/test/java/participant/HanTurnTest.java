package participant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HanTurnTest {

    @Test
    void 이동_후에는_다른_초나라_턴을_반환한다() {
        // given
        Turn turn = new HanTurn();
        // when
        Turn movedTurn = turn.move();
        // then
        assertThat(movedTurn).isInstanceOf(ChoTurn.class);
    }
}