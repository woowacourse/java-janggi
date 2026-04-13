package domain.coordination;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RowTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void 로우_객체를_생성할_때_1부터_10까지의_값이_들어가면_예외가_발생하지_않는다(int index) {

        assertThatCode(() -> {
            new Row(index);
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 11})
    public void 로우_객체를_생성할_때_경계값을_넘는_수인_0_음수_11이_들어가면_예외가_발생한다(int index) {

        assertThatThrownBy(() -> {
            new Row(index);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
