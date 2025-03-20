package domain;

import static domain.Offset.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class OffsetTest {

    @Nested
    class ValidCases {

        @DisplayName("움직임이 없는 오프셋인지 확인한다.")
        @Test
        void hasNoMovement() {
            assertAll(
                    () -> assertThat(new Offset(0, 0).hasNoMovement()).isTrue(),
                    () -> assertThat(new Offset(1, 0).hasNoMovement()).isFalse()
            );
        }

        @DisplayName("해당 오프셋의 단위 방향 오프셋을 반환한다.")
        @ParameterizedTest
        @MethodSource("provideOffsetWithDirectionOffset")
        void getUnitDirectionOffset(final Offset offset, final Offset directionOffset) {
            assertThat(offset.getUnitDirectionOffset()).isEqualTo(directionOffset);
        }

        static Stream<Arguments> provideOffsetWithDirectionOffset() {
            return Stream.of(
                    Arguments.of(new Offset(0, 0), new Offset(0, 0)),
                    Arguments.of(new Offset(0, 4), UP),
                    Arguments.of(new Offset(0, -6), DOWN),
                    Arguments.of(new Offset(7, 0), RIGHT),
                    Arguments.of(new Offset(-2, 0), LEFT),
                    Arguments.of(new Offset(4, 2), RIGHT_UP),
                    Arguments.of(new Offset(6, -3), RIGHT_DOWN),
                    Arguments.of(new Offset(-5, 2), LEFT_UP),
                    Arguments.of(new Offset(-4, -5), LEFT_DOWN)
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("오프셋의 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource(
                value = {
                        "+9, 0",
                        "-9, 0",
                        "0, +10",
                        "0, -10"
                }
        )
        void validateRange(
                int x,
                int y
        ) {
            // when & then
            assertThatThrownBy(() -> new Offset(x, y))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("오프셋의 범위를 벗어났습니다.");
        }
    }
}
