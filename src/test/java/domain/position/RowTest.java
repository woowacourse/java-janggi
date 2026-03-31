package domain.position;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @Test
    @DisplayName("Row가 최소값(1)보다 작으면 예외가 발생한다")
    void should_throw_exception_when_row_is_less_than_minimum() {
        assertThatThrownBy(() -> new Row(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Row가 최대값(10)을 초과하면 예외가 발생한다")
    void should_throw_exception_when_row_is_greater_than_maximum() {
        assertThatThrownBy(() -> new Row(11))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Row가 경계값 내에 있으면 정상적으로 생성된다 (1 ≤ x ≤ 10)")
    void should_create_row_successfully_within_valid_range() {
        assertThatCode(() -> new Row(1))
                .doesNotThrowAnyException();
        assertThatCode(() -> new Row(10))
                .doesNotThrowAnyException();
    }

}
