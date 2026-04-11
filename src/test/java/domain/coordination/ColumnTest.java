package domain.coordination;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9})
    public void 컬럼_객체를_생성할_때_1부터_9까지의_값이_들어가면_예외가_발생하지_않는다(int index) {

        assertThatCode(() -> {
            new Column(index);
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10})
    public void 컬럼_객체를_생성할_때_경계값을_넘는_수인_0_음수_10이_들어가면_예외가_발생한다(int index) {

        assertThatThrownBy(() -> {
            new Column(index);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
