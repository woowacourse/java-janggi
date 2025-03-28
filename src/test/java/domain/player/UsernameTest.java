package domain.player;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsernameTest {

    @Test
    @DisplayName("플레이어의 이름이 10자를 넘으면 예외가 발생한다")
    void validateUsernameLengthException() {
        // given
        String inputName = "나의이름은열자를넘어갑니다";

        // when & then
        assertThatThrownBy(() -> new Username(inputName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("이름은 최대 \\d+자 까지 가능합니다.");
    }

}
