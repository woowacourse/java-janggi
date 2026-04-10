package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GameRuleTest {

    private final GameRule gameRule = new GameRule();

    @DisplayName("첫 번째 턴은 초(CHO)여야 한다")
    @Test
    void firstTurn() {
        assertThat(gameRule.firstTurn()).isEqualTo(Side.CHO);
    }

    @DisplayName("진영별 보너스 점수 검증")
    @Nested
    class BonusPointTest {

        private static final double BONUS_POINT_COMPARISON_OFFSET = 0.001;

        @DisplayName("초(CHO)의 보너스 점수는 0.0점이다")
        @Test
        void choBonus() {
            assertThat(gameRule.bonusPointOf(Side.CHO)).isCloseTo(0.0, offset(BONUS_POINT_COMPARISON_OFFSET));
        }

        @DisplayName("한(HAN)의 보너스 점수는 1.5점이다")
        @Test
        void hanBonus() {
            assertThat(gameRule.bonusPointOf(Side.HAN)).isCloseTo(1.5, offset(BONUS_POINT_COMPARISON_OFFSET));
        }
    }

    @DisplayName("게임 종료 판단 검증")
    @Nested
    class FinishedTest {

        @DisplayName("왕이 잡혔다면(true) 점수와 상관없이 게임은 종료된다")
        @ParameterizedTest
        @CsvSource({
                "30, 10",
                "10, 30",
                "30, 30"
        })
        void finishedByCapture(int choPoint, int hanPoint) {
            assertThat(gameRule.isFinished(true, choPoint, hanPoint)).isTrue();
        }

        @DisplayName("왕이 살아있을 때, 어느 한 쪽이라도 30점 이상이면 게임은 계속된다")
        @ParameterizedTest
        @CsvSource({
                "30, 10",
                "10, 30",
                "30, 30"
        })
        void notFinished(int choPoint, int hanPoint) {
            assertThat(gameRule.isFinished(false, choPoint, hanPoint)).isFalse();
        }

        @DisplayName("왕이 살아있더라도 양쪽 점수가 모두 30점 미만이면 게임은 종료된다")
        @Test
        void finishedByLowPoints() {
            assertThat(gameRule.isFinished(false, 29, 29)).isTrue();
        }
    }
}
