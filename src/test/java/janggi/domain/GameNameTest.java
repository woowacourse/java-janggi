package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GameNameTest {
    @ParameterizedTest
    @ValueSource(strings = {"ab", "가나다라마바사아자차카타파하거너더러머버", "안녕하세요장기게임입니다"})
    void 게임_이름이_2글자_이상_20글자_이하이면_정상적으로_생성된다(String name) {
        GameName gameName = new GameName(name);

        assertThat(gameName.name()).isEqualTo(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "가나다라마바사아자차카타파하거너더러머버서어저처"})
    void 게임_이름이_2글자_미만_20글자_초과면_예외가_발생한다(String name) {

        assertThatThrownBy(() -> new GameName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 2글자 이상, 20글자 이하로만 가능합니다.");
    }
}
