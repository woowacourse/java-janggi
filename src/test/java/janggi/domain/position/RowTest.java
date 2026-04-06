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

class RowTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    @DisplayName("행의 값은 1부터 10까지의 숫자이다")
    public void create_row_success(int row) {
        // when & then
        assertThatCode(() -> new Row(row))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 11})
    @DisplayName("행의 값이 1부터 10까지 숫자가 아니면 예외가 발생한다")
    public void create_row_fail(int row) {
        // when & then
        assertThatThrownBy(() -> new Row(row))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("기존 행에 값을 더해서 새로운 행을 만든다")
    public void add_success() {
        // given
        int rowNum = 1;
        Row row = new Row(1);

        // when
        Row result = row.add(rowNum);

        // then
        assertThat(result).isEqualTo(new Row(2));
    }
    
    @ParameterizedTest
    @CsvSource(value = {
            "-5, false",
            "-4, true",
            "6, false",
            "5, true",
    })
    @DisplayName("기존 행에 특정 값을 더한 결과가 경계 내에 있는지 확인한다.")
    public void isOffsetWithinBounds(int offset, boolean result) {
        // given
        Row row = new Row(5);

        // when
        boolean offsetWithinBounds = row.isOffsetWithinBounds(offset);

        // then
        assertThat(offsetWithinBounds).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 10",
            "3, 8",
            "5, 6",
    })
    @DisplayName("행 객체를 뒤집어서 반환한다.")
    public void flip_success(int r, int result) {

        // when
        Row row = new Row(r);

        // when
        Row resultRow = row.flip();

        // then
        assertThat(resultRow.row()).isEqualTo(result);
    }

}
