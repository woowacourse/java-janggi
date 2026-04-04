package janggi.domain.team;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TurnManagerTest {
    private TurnManager turnManager;

    @BeforeEach
    void setUp() {
        turnManager = new TurnManager();
    }

    @Nested
    @DisplayName("턴 변경 테스트")
    class ChangeTurn {
        @Test
        @DisplayName("처음 턴을 변경하면 초깃값(RED)에서 BLUE로 바뀐다")
        void changeTurn_ToBlue() {
            // when
            turnManager.changeTurn();

            // then
            assertThat(turnManager.isCurrentTurnOf(TeamType.BLUE)).isTrue();
        }

        @Test
        @DisplayName("한번 더 변경하면 BLUE에서 RED로 바뀐다")
        void changeTurn_BackToRed() {
            // when
            turnManager.changeTurn();
            turnManager.changeTurn();

            // then
            assertThat(turnManager.isCurrentTurnOf(TeamType.RED)).isTrue();
        }
    }
}
