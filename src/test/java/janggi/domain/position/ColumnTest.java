package janggi.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ColumnTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 9})
    @DisplayName("열의 값은 1부터 9까지의 숫자이다")
    public void create_column_success(int column) {
        // when & then
        assertThatCode(() -> new Column(column))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10})
    @DisplayName("열의 값이 1부터 9까지 숫자가 아니면 예외가 발생한다")
    public void create_column_success_error(int column) {
        // when & then
        assertThatThrownBy(() -> new Column(column))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("기존 열에 값을 더해서 새로운 열을 만든다")
    public void add_success() {
        // given
        int columnNum = 1;
        Column column = new Column(1);

        // when
        Column result = column.add(columnNum);

        // then
        assertThat(result).isEqualTo(new Column(2));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "-5, false",
            "-4, true",
            "5, false",
            "4, true",
    })
    @DisplayName("기존 열에 특정 값을 더한 결과가 경계 내에 있는지 확인한다.")
    public void isOffsetWithinBounds(int offset, boolean result) {
        // given
        Column column = new Column(5);

        // when
        boolean offsetWithinBounds = column.isOffsetWithinBounds(offset);

        // then
        assertThat(offsetWithinBounds).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 9",
            "3, 7",
            "5, 5",
    })
    @DisplayName("뒤집을지 여부에 따라 적절한 열 객체를 반환한다.")
    public void flip_success(int col, int result) {

        // given
        Column column = new Column(col);

        // when
        Column resultColumn = column.flip();

        // then
        assertThat(resultColumn.column()).isEqualTo(result);
    }

}
