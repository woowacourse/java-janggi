package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TableSettingTest {
    @Test
    @DisplayName("해당하지 않는 상차림을 입력한 경우, 예외가 발생한다.")
    void tableSettingFormatExceptionTest() {
        assertThatThrownBy(() -> TableSetting.from("상상상상"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 상차림입니다.");
    }
}
