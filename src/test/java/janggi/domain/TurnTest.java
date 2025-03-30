package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @DisplayName("올바른 턴이 생성되는 지 확인한다.")
    @Test
    void initializeTurnTest() {

        // given

        // when & then
        assertThatCode(() -> Turn.initialize(BLUE))
                .doesNotThrowAnyException();
    }

    @DisplayName("현재 턴을 받는다.")
    @Test
    void getCurrentTurnTest() {

        // given
        Turn turn = Turn.initialize(BLUE);

        // when & then
        assertThat(turn.getCurrentTurn()).isEqualTo(BLUE);
    }

    @DisplayName("턴을 교체한다.")
    @Test
    void changeTurnTest() {

        // given
        Turn turn = Turn.initialize(BLUE);

        // when & then
        assertAll(() -> {
            assertThat(turn.getCurrentTurn()).isEqualTo(BLUE);
            turn.changeTurn();
            assertThat(turn.getCurrentTurn()).isEqualTo(RED);
        });
    }

    @DisplayName("30턴이 지나면 무승부가 된다.")
    @Test
    void isDrawTest() {
        // given
        Turn turn = Turn.initialize(BLUE);

        // when
        for (int i = 0; i < 30; i++) {
            turn.changeTurn();
        }

        // then
        assertThat(turn.isDraw()).isTrue();
    }
}
