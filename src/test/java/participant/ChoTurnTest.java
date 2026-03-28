package participant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ChoTurnTest {

    @Test
    void 이동_후에는_한나라_턴을_반환한다() {
        // given
        Turn turn = new ChoTurn();
        // when
        Turn movedTurn = turn.move();
        // then
        assertThat(movedTurn).isInstanceOf(HanTurn.class);
    }
}