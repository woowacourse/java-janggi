package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalaceTest {

    @DisplayName("궁에 속하는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 9, true", "4, 9, true", "5, 9, true",
            "3, 8, true", "4, 8, true", "5, 8, true",
            "3, 7, true", "4, 7, true", "5, 7, true",

            "3, 6, false", "4, 6, false", "5, 6, false",
            "3, 5, false", "4, 5, false", "5, 5, false",
            "3, 4, false", "4, 4, false", "5, 4, false",
            "3, 3, false", "4, 3, false", "5, 3, false",

            "3, 2, true", "4, 2, true", "5, 2, true",
            "3, 1, true", "4, 1, true", "5, 1, true",
            "3, 0, true", "4, 0, true", "5, 0, true",
    })
    void isInsidePalaceTest(int x, int y, boolean expected) {
        // given
        Point point = new Point(x, y);

        // when
        boolean actual = Palace.isInsidePalace(point);

        // then
        assertThat(actual)
                .isSameAs(expected);
    }

    @DisplayName("대각선 이동이 허용된 위치인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "3, 7, 4, 8, true",
            "3, 7, 5, 9, true",
            "3, 9, 4, 8, true",
            "3, 9, 5, 7, true",
            "3, 9, 5, 9, false",
            "3, 9, 4, 9, false",
            "0, 0, 4, 8, false",
    })
    void isDiagonalPalaceMoveAllowedTest(int fromX, int fromY, int toX, int toY, boolean expected) {
        // given
        Point fromPoint = new Point(fromX, fromY);
        Point toPoint = new Point(toX, toY);

        // when
        boolean actual = Palace.isDiagonalPalaceMoveAllowed(fromPoint, toPoint);

        // then
        assertThat(actual)
                .isSameAs(expected);
    }
}
