package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ArrangementTest {
    @ParameterizedTest
    @CsvSource({
            "마상마상, MA_SANG_MA_SANG",
            "마상상마, MA_SANG_SANG_MA",
            "상마마상, SANG_MA_MA_SANG",
            "상마상마, SANG_MA_SANG_MA"
    })
    void 한글_배치_이름에_대해서_해당_Arrangement를_반환한다(String input, Arrangement expected) {
        Arrangement result = Arrangement.from(input);
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"마마상상", "상상마마", " ", "unknown"})
    void 존재하지_않는_배치_이름에_대해서_예외가_발생한다(String invalidInput) {
        assertThatThrownBy(() -> Arrangement.from(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 배치입니다.");
    }
}
