package janggi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {

    @Test
    void X좌표와_Y좌표를_입력하여_위치를_생성한다() {
        // Given
        final int y = 4;
        final int x = 3;

        // When & Then
        assertThatCode(() -> new Position(y, x))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2",
            "1, 0",
            "11, 1",
            "1, 10",
    })
    void Y좌표가_1_이상_10_이하가_아니거나_X좌표가_1_이상_9_이하가_아닐_경우_예외가_발생한다(final int y, final int x) {
        // Given

        // When & Then
        assertThatThrownBy(() -> new Position(y, x)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] y좌표는 1 이상 10이하, x좌표는 1이상 9이하여야 합니다.");
    }

    @Test
    void 앞으로_움직인다() {
        // Given
        Position position = new Position(3, 1);

        // When & Then
        assertThat(position.moveForward()).isEqualTo(new Position(2, 1));
    }

    @Test
    void 뒤로_움직인다() {
        // Given
        Position position = new Position(3, 1);

        // When & Then
        assertThat(position.moveBackward()).isEqualTo(new Position(4, 1));
    }

    @Test
    void 오른쪽으로_움직인다() {
        // Given
        Position position = new Position(3, 1);

        // When & Then
        assertThat(position.moveRight()).isEqualTo(new Position(3, 2));
    }

    @Test
    void 왼쪽으로_움직인다() {
        // Given
        Position position = new Position(3, 2);

        // When & Then
        assertThat(position.moveLeft()).isEqualTo(new Position(3, 1));
    }
}