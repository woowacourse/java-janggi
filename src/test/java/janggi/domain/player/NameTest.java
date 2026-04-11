package janggi.domain.player;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void 이름은_빈값이나_공백이_될수없다(String input) {
        assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "12345"})
    void 이름이_2글자_이상이거나_5글자를_이하이면_정상_생성된다(String input) {
        assertThatCode(() -> new Name(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123456"})
    void 이름이_2글자_미만이거나_5글자를_초과하면_예외가_발생한다(String input) {
        assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 2~5글자 사이여야 합니다.");
    }
}
