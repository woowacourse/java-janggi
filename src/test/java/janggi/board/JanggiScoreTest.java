package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiScoreTest {

    @Nested
    @DisplayName("연산")
    class Calculate {

        @DisplayName("덧셈 연산을 올바르게 수행한다.")
        @Test
        void plus() {
            // given
            final JanggiScore janggiScore1 = new JanggiScore(25.5);
            final JanggiScore janggiScore2 = new JanggiScore(10.5);
            final JanggiScore expected = new JanggiScore(36);

            // when
            final JanggiScore actual = janggiScore1.plus(janggiScore2);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("뺄셈 연산을 올바르게 수행한다.")
        @Test
        void minus() {
            // given
            final JanggiScore janggiScore1 = new JanggiScore(25.5);
            final JanggiScore janggiScore2 = new JanggiScore(10.5);
            final JanggiScore expected = new JanggiScore(15);

            // when
            final JanggiScore actual = janggiScore1.minus(janggiScore2);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("더 크다면, true 아니라면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void isGreaterThan(final JanggiScore scoreOfComparison, final boolean expected) {
            // given
            final JanggiScore janggiScore = new JanggiScore(10);

            // when
            final boolean actual = janggiScore.isGreaterThan(scoreOfComparison);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> isGreaterThan(){
            return Stream.of(
                    Arguments.of(new JanggiScore(9), true),
                    Arguments.of(new JanggiScore(10), true),
                    Arguments.of(new JanggiScore(11), false)
            );
        }

    }
}
