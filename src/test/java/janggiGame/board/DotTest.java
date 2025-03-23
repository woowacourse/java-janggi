package janggiGame.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class DotTest {
    public static Stream<Arguments> provideRowAndColumn() {
        return Stream.of(
                Arguments.of(-1, 7),
                Arguments.of(2, 10),
                Arguments.of(9, -1)
        );
    }

    @DisplayName("row 와 column 좌표를 가지고 있는 점을 생성한다.")
    @Test
    void createPosition() {
        // given
        int row = 1;
        int column = 2;

        // when // then
        assertThatCode(() -> new Dot(row, column))
                .doesNotThrowAnyException();

    }

    @DisplayName("row 좌표가 0부터 8까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
    void rowHasBoundary(int row) {
        // given
        int column = 2;

        // when // then
        assertThatCode(() -> Dot.of(row, column))
                .doesNotThrowAnyException();
    }

    @DisplayName("column 좌표가 0부터 9까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void columnHasBoundary(int column) {
        // given
        int row = 2;

        // when // then
        assertThatCode(() -> Dot.of(row, column))
                .doesNotThrowAnyException();
    }

    @DisplayName("범위를 벗어난 점을 조회할 경우 예외 처리한다.")
    @ParameterizedTest
    @MethodSource("provideRowAndColumn")
    void validateDotRange(int row, int column) {

        // when // then
        assertThatCode(() -> Dot.of(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("row 와 column 의 정해진 범위에 맞게 점을 생성 해둔다.")
    @Test
    void createDotsCache() {
        // given
        Dot dotA = Dot.of(1, 1);
        Dot dotB = Dot.of(1, 1);

        // when
        boolean actual = dotA == dotB;

        // then
        assertThat(actual).isTrue();
    }
}