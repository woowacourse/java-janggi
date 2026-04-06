package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.camp.CampType;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 초나라부터_차례를_진행한다() {
        // given
        Turn turn = new Turn();
        // when
        CampType result = turn.currentTurn();
        // then
        assertThat(result).isEqualTo(CampType.CHO);
    }

    @Test
    void 초나라_차례가_끝나면_한나라_차례가_된다() {
        // given
        Turn turn = new Turn();
        CampType firstTurn = turn.currentTurn();
        // when
        turn.finishTurn();
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(firstTurn).isEqualTo(CampType.CHO);
            assertSoftly.assertThat(turn.currentTurn()).isEqualTo(CampType.HAN);
        });
    }
}
