package janggi.point;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PointTest {

    @Nested
    @DisplayName("생성 테스트")
    class CreatePointTest {

        @DisplayName("생성 시 보드의 가로 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(ints = {10, -1})
        void failIfOutOfRowRange(int outOfRangeRow) {

            assertThatThrownBy(() -> new Point(outOfRangeRow, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("보드판의 범위를 벗어난 좌표입니다.");
        }

        @DisplayName("생성 시 보드의 가로 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(ints = {9, -1})
        void failIfOutOfColumnRange(int outOfRangeColumn) {

            assertThatThrownBy(() -> new Point(0, outOfRangeColumn))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("보드판의 범위를 벗어난 좌표입니다.");
        }
    }
}
