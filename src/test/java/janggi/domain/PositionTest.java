package janggi.domain;

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
    void equalTest(final int x, final int y) {

        // given

        // when & then
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드를 벗어났습니다.");
    }

}
