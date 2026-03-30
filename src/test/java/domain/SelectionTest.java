package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SelectionTest {

    @Test
    void 문자열_입력을_Selection으로_변환한다() {
        assertThat(Selection.from("1")).isEqualTo(Selection.FIRST);
        assertThat(Selection.from(" 2 ")).isEqualTo(Selection.SECOND);
        assertThat(Selection.from("3")).isEqualTo(Selection.THIRD);
        assertThat(Selection.from("4")).isEqualTo(Selection.FOURTH);
    }

    @Test
    void 올바르지_않은_입력은_예외가_발생한다() {
        assertThatThrownBy(() -> Selection.from("0"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> Selection.from("5"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
