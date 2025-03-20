package janggi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CurvedMovementTest {

    @ParameterizedTest
    @CsvSource(value = {"0", "-1"})
    @DisplayName("CurvedMovement에서 직선 움직임은 1 이상만 허용된다")
    void shouldStraightMovementIs1orMore(int straightMovement) {
        // given
        int validDiagonalMovement = straightMovement + 1;

        // when
        // then
        assertThatThrownBy(() -> new CurvedMovement(straightMovement, validDiagonalMovement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("직선으로 1칸 이상 움직여야 합니다");
    }

    @Test
    @DisplayName("CurvedMovement는 대각선 움직임이 직선 움직임보다 커야합니다")
    void shouldDiagonalMovementGreaterThanStraightMovement() {
        // given
        int validStraightMovement = 1;
        int validDiagonalMovement = validStraightMovement + 1;

        int invalidStraightMovement = 2;
        int invalidDiagonalMovement = invalidStraightMovement - 1;

        // when
        // then
        assertAll(() -> {
            assertThatCode(() -> new CurvedMovement(validStraightMovement, validDiagonalMovement))
                    .doesNotThrowAnyException();

            assertThatThrownBy(() -> new CurvedMovement(invalidStraightMovement, invalidDiagonalMovement))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("대각선 움직임이 직선 움직임보다 커야 합니다");
        });
    }
}
