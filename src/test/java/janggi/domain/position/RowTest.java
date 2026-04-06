package janggi.domain.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RowTest {
    @ParameterizedTest
    @CsvSource({"0", "9"})
    void 행은_0과_9_사이_값이다(int input) {
        Row row = Row.from(input);
        Assertions.assertThat(row.value()).isEqualTo(input);
    }

    @ParameterizedTest
    @CsvSource({"-1", "10"})
    void 행에_0과_9_범위_밖의_값이_들어오면_예외처리한다(int input) {
        assertThatThrownBy(() -> Row.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("행은 0 ~ 9 입니다.");
    }
}