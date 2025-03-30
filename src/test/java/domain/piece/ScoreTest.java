package domain.piece;

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

        @DisplayName("점수를 더할 수 있다.")
        @Test
        void add() {
            // given
            Score score1 = new Score(10.5f);
            Score score2 = new Score(20.0f);

            // when
            Score result = score1.add(score2);

            // then
            assertThat(result).isEqualTo(new Score(30.5f));
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("점수는 유효한 범위 사이의 값이어야 한다.")
        @ParameterizedTest
        @ValueSource(floats = {-1.0f, -0.01f, 73.51f, 100.0f})
        void validateScore(float invalidValue) {
            // when & then
            assertThatThrownBy(() -> new Score(invalidValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("점수는 0.0 ~ 73.5 사이의 값이어야 합니다.");
        }
    }
}
