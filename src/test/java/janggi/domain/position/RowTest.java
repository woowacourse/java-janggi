package janggi.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class RowTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    public void 행의_값은_1부터_10까지의_숫자이다(int row) {
        // when & then
        assertThatCode(() -> new Row(row))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 11})
    public void 행의_값이_1부터_10까지_숫자가_아니면_예외가_발생한다(int row) {
        // when & then
        assertThatThrownBy(() -> new Row(row))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 기존_행에_값을_더해서_새로운_행을_만든다() {
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
    public void isOffsetWithinBounds(int offset, boolean result) throws Exception {
        // given
        Row row = new Row(5);

        // when
        boolean offsetWithinBounds = row.isOffsetWithinBounds(offset);

        // then
        assertThat(offsetWithinBounds).isEqualTo(result);
    }

}
