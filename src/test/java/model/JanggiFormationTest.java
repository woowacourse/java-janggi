package model;

import model.formation.JanggiFormation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JanggiFormationTest {

    @ParameterizedTest
    @CsvSource({
            "1, SANG_MA_SANG_MA",
            "2, MA_SANG_MA_SANG",
            "3, MA_SANG_SANG_MA",
            "4, SANG_MA_MA_SANG"
    })
    void 번호로_상차림을_찾는다(int order, JanggiFormation expected) {
        JanggiFormation formation = JanggiFormation.from(order);

        assertThat(formation).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5})
    void 잘못된_번호_입력시_예외가_발생한다(int invalidOrder) {
        assertThatThrownBy(() -> JanggiFormation.from(invalidOrder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상차림 번호입니다.");
    }
}
