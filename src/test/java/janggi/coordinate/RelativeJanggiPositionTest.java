package janggi.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RelativeJanggiPositionTest {

    @Nested
    @DisplayName("다음 경로 계산")
    class CalculateNextJanggiPosition {


        @DisplayName("현재 경로에 대해 상대 경로를 계산하여 반환한다.")
        @ParameterizedTest
        @MethodSource
        void calculateNextPosition(final RelativePosition relativePosition, final JanggiPosition expected) {
            // given
            final JanggiPosition now = new JanggiPosition(2, 2);

            // when
            final JanggiPosition actual = relativePosition.calculateNextPosition(now);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> calculateNextPosition() {
            return Stream.of(
                    Arguments.of(RelativePosition.TOP, new JanggiPosition(1, 2)),
                    Arguments.of(RelativePosition.BOTTOM, new JanggiPosition(3, 2)),
                    Arguments.of(RelativePosition.LEFT, new JanggiPosition(2, 1)),
                    Arguments.of(RelativePosition.RIGHT, new JanggiPosition(2, 3)),
                    Arguments.of(RelativePosition.TOP_LEFT_DIAGONAL, new JanggiPosition(1, 1)),
                    Arguments.of(RelativePosition.TOP_RIGHT_DIAGONAL, new JanggiPosition(1, 3)),
                    Arguments.of(RelativePosition.BOTTOM_LEFT_DIAGONAL, new JanggiPosition(3, 1)),
                    Arguments.of(RelativePosition.BOTTOM_RIGHT_DIAGONAL, new JanggiPosition(3, 3)
                    ));
        }

    }
}
