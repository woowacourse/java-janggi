package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PlayerTest {

    @DisplayName("닉네임이 1자 ~ 6자인 플레이어를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "abcd", "abcdef"})
    void createPlayerTest(String nickName) {
        // when, then
        assertThatCode(() -> new Player(nickName))
                .doesNotThrowAnyException();
    }

    @DisplayName("닉네임이 1자 ~ 6자가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "abcdefg"})
    void createPlayerTest_WhenNickNameLengthNotValid(String nickName) {
        // when, then
        assertThatThrownBy(() -> new Player(nickName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 1자 ~ 6자여야합니다.");
    }
}