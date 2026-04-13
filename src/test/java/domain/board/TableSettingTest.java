package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TableSettingTest {
    @ParameterizedTest
    @ValueSource(strings = {"상마상마", "안상차림"})
    @DisplayName("상차림을 정상적으로 입력받는다.")
    void tableSettingTest(String input) {
        assertDoesNotThrow(() -> TableSetting.from(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"상상상상", "안바깥상차림"})
    @DisplayName("해당하지 않는 상차림을 입력한 경우, 예외가 발생한다.")
    void tableSettingFormatExceptionTest(String input) {
        assertThatThrownBy(() -> TableSetting.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 상차림입니다.");
    }
}
