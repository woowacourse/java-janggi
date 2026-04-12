package janggi.domain.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnTest {

    @ParameterizedTest
    @CsvSource({"0", "8"})
    void 열은_0과_8_사이_값이다(int input) {
        Column column = Column.from(input);
        Assertions.assertThat(column.value()).isEqualTo(input);
    }

    @ParameterizedTest
    @CsvSource({"-1", "9"})
    void 열에_0과_8_범위_밖의_값이_들어오면_예외처리한다(int input) {
        assertThatThrownBy(() -> Column.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("열은 0 ~ 8 입니다.");
    }
}