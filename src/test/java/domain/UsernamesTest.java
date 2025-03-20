package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UsernamesTest {

    @Test
    @DisplayName("중복된 이름은 예외가 발생한다.")
    void duplicateNameTest() {
        assertThatThrownBy(() -> new Usernames("a", "a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 이름은 불가합니다.");
    }

    @ParameterizedTest
    @CsvSource({"a,true", "c,false"})
    @DisplayName("이름이 있는지 확인한다.")
    void hasNameTest(String findUsername, boolean expected) {
        // given
        Usernames usernames = new Usernames("a", "b");
        // when
        boolean result = usernames.hasUsername(findUsername);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("다른 이름을 반환한다.")
    void getAnotherPlayerNameTest() {
        // given
        Usernames usernames = new Usernames("a", "b");
        // when
        String anotherPlayerName = usernames.getAnotherPlayerName("a");
        // then
        assertThat(anotherPlayerName).isEqualTo("b");
    }

    @Test
    @DisplayName("다른 이름을 찾을 때 없는 이름을 입력하면 예외가 발생한다.")
    void getAnotherPlayerNameException() {
        // given
        Usernames usernames = new Usernames("a", "b");
        // when & then
        assertThatThrownBy(() -> usernames.getAnotherPlayerName("C"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("없는 이름입니다.");
    }
}