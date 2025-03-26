package janggi.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("같은 위치면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1, 1, 1, 1, true", " 1, 1, 2, 2, false"
    })
    void equalTest(final int firstX, final int firstY, final int secondX, final int secondY, final boolean expected) {

        // given
        Position firstPosition = new Position(firstX, firstY);
        Position secondPosition = new Position(secondX, secondY);

        // when & then
        assertThat(firstPosition.equals(secondPosition)).isEqualTo(expected);
    }

    @DisplayName("포지션의 유효 범위를 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "-1, 1", "9, 1", "1, -1", "2, 10"
    })
    void validateRangeTest(final int x, final int y) {

        // given

        // when & then
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드를 벗어났습니다.");
    }

    @DisplayName("궁성의 영역인지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "3, 0", "4, 0", "5, 0", "3, 1", "4, 1", "5, 1", "3, 2", "4, 2", "5, 2",
            "3, 7", "4, 7", "5, 7", "3, 8", "4, 8", "5, 8", "3, 9", "4, 9", "5, 9"
    })
    void validatePalaceTrueTest(final int x, final int y) {
        // given

        // when & then
        assertThat(new Position(x, y).isPalace()).isTrue();
    }

    @DisplayName("궁성의 영역이 아닌지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2,1", "2, 2", "4, 3", "6, 2", "6, 1",
            "2, 9", "4, 6", "6, 7", "6, 9"
    })
    void validatePalaceFalseTest(final int x, final int y) {
        // given

        // when & then
        assertThat(new Position(x, y).isPalace()).isFalse();
    }
}
