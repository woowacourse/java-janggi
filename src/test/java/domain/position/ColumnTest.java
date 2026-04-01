package domain.position;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {

    @Test
    @DisplayName("column이 최소값(1)보다 작으면 예외가 발생한다")
    void should_throw_exception_when_column_is_less_than_minimum() {
        assertThatThrownBy(() -> new Column(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("column이 최대값(9)을 초과하면 예외가 발생한다")
    void should_throw_exception_when_column_is_greater_than_maximum() {
        assertThatThrownBy(() -> new Column(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("column가 경계값 내에 있으면 정상적으로 생성된다 (1 ≤ y ≤ 9) ")
    void should_create_column_successfully_within_valid_range() {
        assertThatCode(() -> new Column(1))
                .doesNotThrowAnyException();
        assertThatCode(() -> new Column(9))
                .doesNotThrowAnyException();
    }

}
