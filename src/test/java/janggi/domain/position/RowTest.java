package janggi.domain.position;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RowTest {

    @ParameterizedTest(name = "행 좌표={0}")
    @ValueSource(ints = {1, 10})
    void 유효한_행좌표로_행을_생성하면_좌표값을_가지고_있다(int value) {
        Row row = new Row(value);
        assertThat(row.getValue()).isEqualTo(value);
    }

    @ParameterizedTest(name = "행 좌표={0}")
    @ValueSource(ints = {0, 11})
    void 범위_밖의_행좌표로_행을_생성하면_예외가_발생한다(int value) {
        assertThatThrownBy(() -> new Row(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다");
    }
}
