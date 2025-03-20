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
        assertThatCode(Turn::initialize)
                .doesNotThrowAnyException();
    }

    @DisplayName("현재 턴을 받는다.")
    @Test
    void getCurrentTurnTest() {

        // given
        Turn turn = Turn.initialize();

        // when & then
        assertThat(turn.getCurrentTurn()).isEqualTo(BLUE);
    }

    @DisplayName("턴을 교체한다.")
    @Test
    void changeTurnTest() {

        // given
        Turn turn = Turn.initialize();

        // when & then
        assertAll(() -> {
            assertThat(turn.getCurrentTurn()).isEqualTo(BLUE);
            turn.changeTurn();
            assertThat(turn.getCurrentTurn()).isEqualTo(RED);
        });
    }
}
