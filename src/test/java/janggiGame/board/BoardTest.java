package janggiGame.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class BoardTest {
    public static Stream<Arguments> provideXY() {
        return Stream.of(
                Arguments.of(-1, 7),
                Arguments.of(2, 10),
                Arguments.of(9, -1)
        );
    }

    @DisplayName("x 좌표가 0부터 8까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
    void xHasBoundary(int x) {
        // given
        int y = 2;

        // when // then
        assertThatCode(() -> Board.findBy(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("y 좌표가 0부터 9까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void yHasBoundary(int y) {
        // given
        int x = 2;

        // when // then
        assertThatCode(() -> Board.findBy(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("보드 범위를 벗어난 점을 조회할 경우 예외 처리한다.")
    @ParameterizedTest
    @MethodSource("provideXY")
    void validateDotRange(int x, int y) {

        // when // then
        assertThatCode(() -> Board.findBy(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("x와 y의 정해진 범위에 맞게 점을 생성 해둔다.")
    @Test
    void createDotsCache() {
        // given
        Dot dotA = Board.findBy(1, 1);
        Dot dotB = Board.findBy(1, 1);

        // when
        boolean actual = dotA == dotB;

        // then
        assertThat(actual).isTrue();
    }
}