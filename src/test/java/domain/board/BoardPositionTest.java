package domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class BoardPositionTest {

    @Nested
    class ValidCases {

        @DisplayName("궁성 영역 내부인지 확인한다.")
        @ParameterizedTest
        @MethodSource("providePalaceAreaPositions")
        void isPalaceArea(BoardPosition palacePosition) {
            // when & then
            assertAll(
                    () -> assertThat(palacePosition.isPalaceArea()).isTrue(),
                    () -> assertThat(new BoardPosition(7, 0).isPalaceArea()).isFalse()
            );
        }

        static Stream<Arguments> providePalaceAreaPositions() {
            return Stream.of(
                    Arguments.of(
                            new BoardPosition(3, 0), new BoardPosition(3, 1), new BoardPosition(3, 2),
                            new BoardPosition(3, 7), new BoardPosition(3, 8), new BoardPosition(3, 9),
                            new BoardPosition(4, 0), new BoardPosition(4, 1), new BoardPosition(4, 2),
                            new BoardPosition(4, 7), new BoardPosition(4, 8), new BoardPosition(4, 9),
                            new BoardPosition(5, 0), new BoardPosition(5, 1), new BoardPosition(5, 2),
                            new BoardPosition(5, 7), new BoardPosition(5, 8), new BoardPosition(5, 9)
                    )
            );
        }

        @DisplayName("위치에서 오프셋을 더한다.")
        @Test
        void plus() {
            // given
            BoardPosition position = new BoardPosition(0, 0);
            Offset offset = new Offset(2, 3);

            // when & then
            assertThat(position.plus(offset)).isEqualTo(new BoardPosition(2, 3));
        }

        @DisplayName("위치 사이의 거리를 계산한다.")
        @Test
        void calculateOffset() {
            // given
            BoardPosition before = new BoardPosition(0, 0);
            BoardPosition after = new BoardPosition(1, 2);

            // when
            Offset offset = after.calculateOffset(before);

            // then
            assertThat(offset).isEqualTo(new Offset(1, 2));
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("포지션의 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource(
                value = {
                        "-1, 0",
                        "9, 0",
                        "0, -1",
                        "0, 10"
                }
        )
        void validateRange(
                int x,
                int y
        ) {
            // when & then
            assertThatThrownBy(() -> new BoardPosition(x, y))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("장기판의 범위를 벗어났습니다.");
        }
    }
}
