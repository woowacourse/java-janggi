package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MovementTest {

    @Nested
    class ValidCases {

        @DisplayName("궁성 내부임을 판별한다")
        @ParameterizedTest
        @MethodSource("providePalaceMovementCases")
        void isPalaceMovement(
            BoardPosition from,
            BoardPosition to,
            boolean expected
        ) {
            // given
            Movement movement = new Movement(from, to);

            // when
            boolean result = movement.isPalaceMovement();

            // then
            assertThat(result).isEqualTo(expected);
        }

        // @formatter:off
        static Stream<Arguments> providePalaceMovementCases() {
            return Stream.of(
                Arguments.of(BoardPosition.RED_PALACE_MIDDLE_CENTER, BoardPosition.RED_PALACE_NORTH_CENTER, true),
                Arguments.of(new BoardPosition(1, 1), new BoardPosition(1, 2), false),
                Arguments.of(BoardPosition.RED_PALACE_NORTH_CENTER, BoardPosition.RED_PALACE_MIDDLE_WEST, false)
            );
        }
        // @formatter:on

        @DisplayName("두 위치 사이의 오프셋을 계산한다")
        @Test
        void calculateOffset() {
            // given
            BoardPosition from = new BoardPosition(1, 1);
            BoardPosition to = new BoardPosition(4, 5);
            Movement movement = new Movement(from, to);

            // when
            Offset result = movement.calculateOffset();

            // then
            assertThat(result).isEqualTo(new Offset(3, 4));
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("시작 위치 또는 도착 위치가 null이면 예외가 발생한다")
        @ParameterizedTest
        @MethodSource("provideNullArguments")
        void validateNotNull(
            BoardPosition from,
            BoardPosition to
        ) {
            // when & then
            assertThatThrownBy(() -> new Movement(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("움직임은 시작 위치와 도착 위치를 가져야 합니다.");
        }

        static Stream<Arguments> provideNullArguments() {
            return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, new BoardPosition(0, 0)),
                Arguments.of(new BoardPosition(0, 0), null)
            );
        }
    }
}
