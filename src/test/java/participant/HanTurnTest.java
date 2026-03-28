package participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pieces.Side;

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

    @Test
    void 입력_받은_진영이_한나라인_경우_진영이_일치한다() {
        // given
        Turn turn = new HanTurn();
        Side side = Side.HAN;
        // when
        boolean matchSide = turn.isMatchSide(side);
        // then
        assertThat(matchSide).isTrue();
    }

    @Test
    void 입력_받은_진영이_초나라인_경우_진영이_일치하지_않는다() {
        // given
        Turn turn = new HanTurn();
        Side side = Side.CHO;
        // when
        boolean matchSide = turn.isMatchSide(side);
        // then
        assertThat(matchSide).isFalse();
    }
}