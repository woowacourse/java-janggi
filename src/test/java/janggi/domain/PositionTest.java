package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Nested
    @DisplayName("생성 테스트")
    class Constructor {

        @Test
        @DisplayName("정상 테스트")
        void success() {
            int row = 1;
            int column = 1;

            assertDoesNotThrow(() -> Position.valueOf(row, column));
        }

        @Test
        @DisplayName("행 값이 범위를 벗어난 경우")
        void failure_1() {
            int wrongRow = 11;
            int column = 1;

            assertThatIllegalArgumentException()
                .isThrownBy(() -> Position.valueOf(wrongRow, column));
        }

        @Test
        @DisplayName("열 값이 범위를 벗어난 경우")
        void failure_2() {
            int wrongRow = 1;
            int column = 10;

            assertThatIllegalArgumentException()
                .isThrownBy(() -> Position.valueOf(wrongRow, column));
        }
    }
}