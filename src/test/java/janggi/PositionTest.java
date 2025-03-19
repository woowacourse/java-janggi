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
    void Y좌표끼리의_위치_차이를_구한다() {
        // Given
        Position position1 = new Position(3, 3);
        Position position2 = new Position(6, 3);

        // When & Then
        assertThat(position1.calculateDifferenceForY(position2)).isEqualTo(-3);
    }

    @Test
    void x좌표끼리의_위치_차이를_구한다() {
        // Given
        Position position1 = new Position(1, 6);
        Position position2 = new Position(1, 3);

        // When & Then
        assertThat(position1.calculateDifferenceForX(position2)).isEqualTo(3);
    }
}