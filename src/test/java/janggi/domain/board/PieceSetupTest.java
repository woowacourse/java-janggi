package janggi.domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PieceSetupTest {

    @DisplayName("올바르지 않은 차림 번호를 입력하면 예외가 발생한다.")
    @ParameterizedTest(name = "입력={0}")
    @ValueSource(strings = {"0", "5", "a"})
    void 올바르지_않은_차림_번호를_입력하면_예외가_발생한다(String setupNumber) {
        // when & then
        assertThatThrownBy(() -> PieceSetup.from(setupNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바른 차림 번호를 입력해주세요.");
    }
}
