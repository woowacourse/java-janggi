package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Camp;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 초나라부터_차례를_진행한다() {
        // given
        Turn turn = new Turn();
        // when
        Camp result = turn.currentTurn();
        // then
        assertThat(result).isEqualTo(Camp.CHO);
    }

    @Test
    void 초나라_차례가_끝나면_한나라_차례가_된다() {
        // given
        Turn turn = new Turn();
        Camp firstTurn = turn.currentTurn();
        // when
        turn.finishTurn();
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(firstTurn).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(turn.currentTurn()).isEqualTo(Camp.HAN);
        });
    }
}
