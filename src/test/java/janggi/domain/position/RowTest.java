package janggi.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RowTest {
    @DisplayName("유효한 행 좌표로 행을 생성하면 좌표값을 가지고 있다.")
    @ParameterizedTest(name = "행 좌표={0}")
    @ValueSource(ints = {1, 10})
    void 행_좌표_생성_테스트(int value) {
        // when
        Row row = new Row(value);

        // then
        assertThat(row.getValue()).isEqualTo(value);
    }

    @DisplayName("범위 밖의 행 좌표로 행을 생성하면 예외가 발생한다.")
    @ParameterizedTest(name = "행 좌표={0}")
    @ValueSource(ints = {0, 11})
    void 행_좌표_생성_예외_테스트(int value) {
        // when & then
        assertThatThrownBy(() -> new Row(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다");
    }
}
