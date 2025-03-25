package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("X좌표와 Y좌표를 생성한다.")
    @Test
    void createTest() {
        // given
        int x = 1;
        int y = 2;

        // when & then
        assertThatCode(() -> new Position(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("같은 수평선상에 있는지 확인한다.")
    @Test
    void horizontalTest() {
        // given
        Position position = new Position(0, 0);
        Position otherPosition = new Position(2, 0);

        // when & then
        assertThat(position.isHorizontal(otherPosition))
                .isTrue();
    }

    @DisplayName("같은 수직선상에 있는지 확인한다.")
    @Test
    void verticalTest() {
        // given
        Position position = new Position(0, 0);
        Position otherPosition = new Position(0, 2);

        // when & then
        assertThat(position.isVertical(otherPosition))
                .isTrue();
    }

    @DisplayName("같은 좌표에 있는지 확인한다.")
    @Test
    void equalsTest() {
        // given
        Position position = new Position(0, 0);
        Position otherPosition = new Position(0, 0);

        // when & then
        assertThat(position.equals(otherPosition))
                .isTrue();
    }
}
