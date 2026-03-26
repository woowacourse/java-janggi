package domain.position;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {
    @Test
    @DisplayName("column이 최소값(1)보다 작으면 예외가 발생한다")
    void column_최소값_미만() {
        assertThatThrownBy(() -> new Position(5, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("column이 최대값(9)을 초과하면 예외가 발생한다")
    void column_최대값_초과() {
        assertThatThrownBy(() -> new Position(5, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("column와 row가 경계값 내에 있으면 정상적으로 생성된다 (1 ≤ row ≤ 10, 1 ≤ column ≤ 9)")
    void 정상_범위() {
        assertThatCode(() -> new Position(10, 9))
                .doesNotThrowAnyException();
    }
}