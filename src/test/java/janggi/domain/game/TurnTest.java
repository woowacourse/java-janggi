package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @DisplayName("초기화 시 초나라 진영의 턴을 생성한다")
    @Test
    void init_CreatesTurnWithChoSide() {
        Turn turn = Turn.init();

        assertThat(turn.isTurnOf(Side.CHO)).isTrue();
    }

    @DisplayName("다음 턴을 요청하면 반대 진영의 새로운 턴 객체를 반환한다")
    @Test
    void next_ReturnsNewTurnWithOppositeSide() {
        Turn currentTurn = Turn.init();

        Turn nextTurn = currentTurn.next();

        assertThat(nextTurn.isTurnOf(Side.HAN)).isTrue();
        assertThat(currentTurn).isNotSameAs(nextTurn);
    }

    @DisplayName("주어진 진영이 현재 턴의 진영과 일치하는지 확인한다")
    @Test
    void isTurnOf_ReturnsTrueIfSideMatches() {
        Turn turn = Turn.init();

        assertThat(turn.isTurnOf(Side.CHO)).isTrue();
        assertThat(turn.isTurnOf(Side.HAN)).isFalse();
    }
}
