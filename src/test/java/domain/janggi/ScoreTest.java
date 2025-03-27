package domain.janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ScoreTest {

    @Nested
    class ValidCases {

        @DisplayName("점수를 더한다.")
        @Test
        void plus() {
            // given
            Score score = new Score(10);
            Score addScore = new Score(3);

            // when
            Score sumScore = score.plus(addScore);

            // then
            assertThat(sumScore).isEqualTo(new Score(13));
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("점수가 0점 미만이거나, 73.5점 초과라면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(floats = {-1, 74.5f, 74.0f, -0.5f})
        void validateRange(float value) {
            assertThatThrownBy(() -> new Score(value))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("잘못된 범위의 점수입니다.");
        }


        @DisplayName("점수의 소수점 자리가 0.0이나 0.5가 아니라면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(floats = {1.1f, 73.4f, 5.51f, 0.01f})
        void validateDecimalPart(float value) {
            assertThatThrownBy(() -> new Score(value))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("점수의 소수점자리는 0.0이나 0.5 이어야합니다.");
        }
    }
}
