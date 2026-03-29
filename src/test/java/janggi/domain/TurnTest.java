package janggi.domain;

import janggi.domain.piece.Camp;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 첫번쨰_턴은_초나라가_시작한다() {
        //given
        Turn turn = new Turn();
        //when
        //then
        Assertions.assertThat(turn.currentTurn()).isEqualTo(Camp.CHO);
    }

    @Test
    void 자신의_턴_종료_후_다음_턴은_반드시_상대_진영이다() {
        //given
        Turn turn = new Turn();
        //when
        Camp firstTurn = turn.currentTurn();
        turn.finishTurn();
        Camp secondTurn = turn.currentTurn();
        turn.finishTurn();
        Camp thirdTurn = turn.currentTurn();
        //then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(firstTurn).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(secondTurn).isEqualTo(Camp.HAN);
            assertSoftly.assertThat(thirdTurn).isEqualTo(Camp.CHO);
        });
    }

}
