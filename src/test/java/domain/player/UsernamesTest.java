package domain.player;

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
        // given
        Username name = new Username("a");
        Username other = new Username("a");

        assertThatThrownBy(() -> new Usernames(name, other))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 이름은 불가합니다.");
    }

    @ParameterizedTest
    @CsvSource({"a,true", "b, true", "c,false"})
    @DisplayName("이름이 있는지 확인한다.")
    void hasNameTest(String findName, boolean expected) {
        // given
        Username usernameA = new Username("a");
        Username usernameB = new Username("b");
        Usernames usernames = new Usernames(usernameA, usernameB);
        Username findUsername = new Username(findName);
        // when
        boolean result = usernames.hasUsername(findUsername);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"a, b", "b, a"})
    @DisplayName("다른 이름을 반환한다.")
    void getAnotherPlayerNameTest(String nameA, String nameB) {
        // given
        Username usernameA = new Username(nameA);
        Username usernameB = new Username(nameB);
        Usernames usernames = new Usernames(usernameA, usernameB);
        // when
        Username anotherPlayerName = usernames.getAnotherPlayerName(new Username("a"));
        // then
        assertThat(anotherPlayerName).isEqualTo(new Username("b"));
    }

    @Test
    @DisplayName("다른 이름을 찾을 때 없는 이름을 입력하면 예외가 발생한다.")
    void getAnotherPlayerNameException() {
        // given
        Username usernameA = new Username("a");
        Username usernameB = new Username("b");
        Usernames usernames = new Usernames(usernameA, usernameB);
        // when & then
        assertThatThrownBy(() -> usernames.getAnotherPlayerName(new Username("c")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("없는 이름입니다.");
    }
}
