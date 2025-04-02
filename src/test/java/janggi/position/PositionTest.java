package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.position.Position;
import janggi.exception.ErrorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("X좌표와 Y좌표를 생성한다.")
    @Test
    void createTest() {
        // given
        int x = 1;
        int y = 2;

        // when & then
        assertThatCode(() -> Position.of(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("같은 수평선상에 있는지 확인한다.")
    @Test
    void horizontalTest() {
        // given
        Position position = Position.of(0, 0);
        Position otherPosition = Position.of(2, 0);

        // when & then
        assertThat(position.isHorizontalTo(otherPosition))
                .isTrue();
    }

    @DisplayName("같은 수직선상에 있는지 확인한다.")
    @Test
    void verticalTest() {
        // given
        Position position = Position.of(0, 0);
        Position otherPosition = Position.of(0, 2);

        // when & then
        assertThat(position.isVerticalTo(otherPosition))
                .isTrue();
    }

    @DisplayName("같은 좌표에 있는지 확인한다.")
    @Test
    void equalsTest() {
        // given
        Position position = Position.of(0, 0);
        Position otherPosition = Position.of(0, 0);

        // when & then
        assertThat(position.equals(otherPosition))
                .isTrue();
    }

    @DisplayName("9x10 크기의 장기 보드판의 좌표값이 아닌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "8,10",
            "0,10",
            "9,9",
            "9,0"
    })
    void shouldThrowException_WhenInvalidPosition(int x, int y) {
        // given & when & then
        assertThatCode(() -> Position.of(x, y))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("9x10 크기의 장기 보드판의 좌표값만 가능합니다.");
    }
}
