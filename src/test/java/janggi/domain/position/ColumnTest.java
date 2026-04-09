package janggi.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ColumnTest {

    @DisplayName("유요한 열 좌표로 열을 생성하면 좌표값을 가지고 있다.")
    @ParameterizedTest(name = "열 좌표={0}")
    @ValueSource(ints = {1, 9})
    void 유효한_열좌표로_열을_생성하면_좌표값을_가지고_있다(int value) {
        // when
        Column column = new Column(value);

        // then
        assertThat(column.getValue()).isEqualTo(value);
    }

    @DisplayName("범위 밖의 열 좌표로 열을 생성하면 예외가 발생한다.")
    @ParameterizedTest(name = "열 좌표={0}")
    @ValueSource(ints = {0, 10})
    void 범위_밖의_열좌표로_열을_생성하면_예외가_발생한다(int value) {
        // when & then
        assertThatThrownBy(() -> new Column(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다");
    }
}
