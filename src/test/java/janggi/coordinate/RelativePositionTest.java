package janggi.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.coordinate.Position;
import janggi.coordinate.RelativePosition;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RelativePositionTest {

    @Nested
    @DisplayName("다음 경로 계산")
    class CalculateNextPosition {


        @DisplayName("현재 경로에 대해 상대 경로를 계산하여 반환한다.")
        @ParameterizedTest
        @MethodSource
        void calculateNextPosition(final RelativePosition relativePosition, final Position expected) {
            // given
            final Position now = new Position(2, 2);

            // when
            final Position actual = relativePosition.calculateNextPosition(now);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> calculateNextPosition() {
            return Stream.of(
                    Arguments.of(RelativePosition.TOP, new Position(1, 2)),
                    Arguments.of(RelativePosition.BOTTOM, new Position(3, 2)),
                    Arguments.of(RelativePosition.LEFT, new Position(2, 1)),
                    Arguments.of(RelativePosition.RIGHT, new Position(2, 3)),
                    Arguments.of(RelativePosition.TOP_LEFT_DIAGONAL, new Position(1, 1)),
                    Arguments.of(RelativePosition.TOP_RIGHT_DIAGONAL, new Position(1, 3)),
                    Arguments.of(RelativePosition.BOTTOM_LEFT_DIAGONAL, new Position(3, 1)),
                    Arguments.of(RelativePosition.BOTTOM_RIGHT_DIAGONAL, new Position(3, 3)
                    ));
        }

    }
}
