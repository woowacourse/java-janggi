package participant;

import static org.assertj.core.api.Assertions.assertThat;

import core.Turn;
import org.junit.jupiter.api.Test;
import pieces.Side;

class TurnTest {

    @Test
    void 초나라_턴에서_이동_후에는_한나라_턴을_반환한다() {
        // given
        Turn turn = Turn.CHO_TURN;
        // when
        Turn movedTurn = turn.other();
        // then
        assertThat(movedTurn.getSide()).isEqualTo(Side.HAN);
    }

    @Test
    void 초나라_턴에서_이동_후에는_다른_초나라_턴을_반환한다() {
        // given
        Turn turn = Turn.HAN_TURN;
        // when
        Turn movedTurn = turn.other();
        // then
        assertThat(movedTurn.getSide()).isEqualTo(Side.CHO);
    }
}