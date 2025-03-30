package domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.TeamType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlayersTest {

    @Test
    @DisplayName("플레이어들의 이름과 시작할 플레이어의 이름으로 객체를 생성한다")
    void createFromTest() {
        // given
        Usernames usernames = new Usernames(new Username("루키"), new Username("피케이"));
        Username startPlayerName = new Username("루키");

        // when & then
        assertThatCode(() -> Players.createFrom(usernames, startPlayerName))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("입력받은 플레이어의 이름들에 시작할 플레이어의 이름이 없으면 예외가 발생한다")
    void validateHasNameTest() {
        // given
        Usernames usernames = new Usernames(new Username("루키"), new Username("피케이"));
        Username startPlayerName = new Username("루키페어코기");

        // when & then
        assertThatThrownBy(() -> Players.createFrom(usernames, startPlayerName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않은 이름입니다.");
    }

    @Test
    @DisplayName("초의 팀에 해당하는 플레이어의 이름을 반환한다")
    void getChoPlayerNameTest() {
        // given
        Usernames usernames = new Usernames(new Username("루키"), new Username("피케이"));
        Username startPlayerName = new Username("루키");
        Players players = Players.createFrom(usernames, startPlayerName);

        // when
        String choPlayerName = players.getChoPlayerName();
        assertThat(choPlayerName).isEqualTo("루키");
    }

    @Test
    @DisplayName("한의 팀에 해당하는 플레이어의 이름을 반환한다")
    void getHanPlayerNameTest() {
        // given
        Usernames usernames = new Usernames(new Username("루키"), new Username("피케이"));
        Username startPlayerName = new Username("루키");
        Players players = Players.createFrom(usernames, startPlayerName);

        // when
        String choPlayerName = players.getHanPlayerName();
        assertThat(choPlayerName).isEqualTo("피케이");
    }

    static Stream<Arguments> getTeamPlayerTest() {
        return Stream.of(
                Arguments.of(TeamType.CHO, new Player(new Username("루키"), TeamType.CHO)),
                Arguments.of(TeamType.HAN, new Player(new Username("피케이"), TeamType.HAN))
        );
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("각 팀에 맞는 플레이어를 반환한다")
    void getTeamPlayerTest(TeamType teamType, Player expected) {
        // given
        Usernames usernames = new Usernames(new Username("루키"), new Username("피케이"));
        Username startPlayerName = new Username("루키");
        Players players = Players.createFrom(usernames, startPlayerName);

        // when
        Player actual = players.getTeamPlayer(teamType);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
