package domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("column이 최소값보다 작으면 false를 반환한다")
    void should_return_false_when_column_is_less_than_minimum() {
        // given
        Column column = new Column(5);
        // when & then
        assertThat(column.isOutBoundColumn(0)).isTrue();
    }

    @Test
    @DisplayName("column이 최대값보다 크면 false를 반환한다")
    void should_return_false_when_column_is_greater_than_maximum() {
        // given
        Column column = new Column(5);
        // when & then
        assertThat(column.isOutBoundColumn(11)).isTrue();
    }

    @Test
    @DisplayName("column이 유효 범위 내이면 true를 반환한다")
    void should_return_true_when_column_is_within_valid_range() {
        // given
        Column column = new Column(5);
        // when & then
        assertThat(column.isOutBoundColumn(5)).isFalse();
    }

}
